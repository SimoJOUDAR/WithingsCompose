package com.mjoudar.withingscompose.presentation.ui.screens.result_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val context = LocalContext.current.applicationContext
    val owner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.createGif(context, sharedViewModel.links, owner)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DisplayResult(gifUiState)
    }
}
