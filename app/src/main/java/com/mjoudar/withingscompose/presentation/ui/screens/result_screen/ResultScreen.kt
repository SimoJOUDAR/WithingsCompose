package com.mjoudar.withingscompose.presentation.ui.screens.result_screen

import android.content.Context
import android.content.res.TypedArray
import android.graphics.ImageDecoder
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.work.WorkInfo
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mjoudar.withingscompose.R
import com.mjoudar.withingscompose.presentation.SharedViewModel
import com.mjoudar.withingscompose.presentation.ui.components.DisplayGif
import com.mjoudar.withingscompose.presentation.ui.components.ErrorPage
import com.mjoudar.withingscompose.presentation.ui.components.Processing
import com.mjoudar.withingscompose.utils.GIF_FILE_NAME
import kotlinx.coroutines.launch


@Composable
fun ResultScreen(
    context: Context,
    sharedViewModel: SharedViewModel
) {

    val viewModel: ResultViewModel = hiltViewModel()
    val requestCodePermissions = 111
    val gifUiState by viewModel.processingState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            when (val state = gifUiState) {
                ResultViewModel.GifUiState.Loading -> Processing()
                is ResultViewModel.GifUiState.Success -> DisplayGif(state.fileName)
                is ResultViewModel.GifUiState.Error -> ErrorPage()
            }
        }
        viewModel.createGif(context, sharedViewModel.links)
    }
}
