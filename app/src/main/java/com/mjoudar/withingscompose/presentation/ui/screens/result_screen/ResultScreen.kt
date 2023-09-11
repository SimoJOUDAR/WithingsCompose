package com.mjoudar.withingscompose.presentation.ui.screens.result_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mjoudar.withingscompose.presentation.SharedViewModel
import com.mjoudar.withingscompose.presentation.ui.components.DisplayResult


@Composable
fun ResultScreen(sharedViewModel: SharedViewModel) {
    val viewModel = hiltViewModel<ResultViewModel>()
    val gifUiState by viewModel.processingState.collectAsState()
    viewModel.createGif(LocalContext.current, sharedViewModel.links, LocalLifecycleOwner.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DisplayResult(gifUiState)
    }
}
