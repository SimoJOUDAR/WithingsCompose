package com.mjoudar.withingscompose.presentation.ui.screens.result_screen

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mjoudar.withingscompose.domain.processing.GifCreatorWorker
import com.mjoudar.withingscompose.utils.GIF_FILE_NAME
import com.mjoudar.withingscompose.utils.GIF_WORKER
import com.mjoudar.withingscompose.utils.IMAGE_URLS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor() : ViewModel() {

    private val _processingState = MutableStateFlow<GifUiState>(GifUiState.Loading)
    val processingState = _processingState.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = GifUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    fun createGif(context: Context, urls: Array<String>, localLifecycleOwner: LifecycleOwner) {

        val inputData = Data.Builder()
            .putStringArray(IMAGE_URLS, urls)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<GifCreatorWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        with(WorkManager.getInstance(context)) {
            beginUniqueWork(GIF_WORKER, ExistingWorkPolicy.KEEP, workRequest).enqueue()
            getWorkInfoByIdLiveData(workRequest.id)
                .observe(localLifecycleOwner) { workInfo ->
                    workInfo?.let {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                workInfo.outputData.getString(GIF_FILE_NAME)?.let {
                                    emitData(it)
                                } ?: emitError()
                            }

                            WorkInfo.State.FAILED -> emitError()

                            else -> {}
                        }
                    }
                }
        }
    }

    private fun emitData(fileName: String) = viewModelScope.launch(Dispatchers.IO) {
        _processingState.emit(GifUiState.Success(fileName))
    }

    private fun emitError() = viewModelScope.launch(Dispatchers.IO) {
        _processingState.emit(GifUiState.Error(null))
    }

    sealed class GifUiState {
        object Loading : GifUiState()
        data class Success(val fileName: String) : GifUiState()
        data class Error(val error: Exception?) : GifUiState()
    }
}