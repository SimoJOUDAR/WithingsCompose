package com.mjoudar.withingscompose.presentation.ui.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjoudar.withingscompose.data.repositories.ImagesRepository
import com.mjoudar.withingscompose.domain.models.ImageInfo
import com.mjoudar.withingscompose.utils.toImageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {

    private var items = mutableListOf<ImageInfo>()

    private val _imageLot = MutableStateFlow<ImagesUiState>(ImagesUiState.Loading)
    val imageLot = _imageLot.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = ImagesUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        getResults("panda")
    }

    fun getResults(input: String) = viewModelScope.launch(Dispatchers.IO) {
        val defaultException =
            Exception("An unidentified error occurred. We couldn't load the data. Please, check your internet connection.")
        val response = repository.getImages(input)
        if (response.succeeded) {
            items = response.body.hits?.map { it.toImageInfo() }?.toMutableList() ?: mutableListOf()
            _imageLot.emit(ImagesUiState.Success(items))
        } else {
            val e = response.exception ?: defaultException
            _imageLot.emit(ImagesUiState.Error(e))
        }
    }

    fun itemClicked(position: Int) {
        items[position] = items[position].copy(isChecked = !items[position].isChecked)
        viewModelScope.launch(Dispatchers.IO) {
            _imageLot.emit(ImagesUiState.Success(items, position))
        }
    }

    fun getSelectedItems(): Array<String> {
        return items.filter { it.isChecked }.map { it.displayUrl }.toTypedArray()
    }

    sealed class ImagesUiState {
        object Loading : ImagesUiState()
        data class Success(val images: List<ImageInfo>, val position: Int? = null) : ImagesUiState()
        data class Error(val error: Exception) : ImagesUiState()
    }
}