package com.mjoudar.withingscompose.presentation.ui.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mjoudar.withingscompose.R
import com.mjoudar.withingscompose.presentation.NavGraph.ResultScreenRoute
import com.mjoudar.withingscompose.presentation.SharedViewModel
import com.mjoudar.withingscompose.presentation.ui.components.ErrorPage
import com.mjoudar.withingscompose.presentation.ui.components.HomeScreenGrid
import com.mjoudar.withingscompose.presentation.ui.components.SearchBar
import com.mjoudar.withingscompose.presentation.ui.components.ShimmerEffect
import com.mjoudar.withingscompose.presentation.ui.theme.LatoTypograpgy
import com.mjoudar.withingscompose.presentation.ui.theme.PictonBlue


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val viewModel: HomeViewModel = hiltViewModel()
    val imagesUiState by viewModel.imageLot.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Home_screen_title),
                style = LatoTypograpgy.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(onSearch = {
                viewModel.getResults(it)
                sharedViewModel.links = arrayOf()
            })
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = imagesUiState) {
                HomeViewModel.ImagesUiState.Loading -> {
                    HomeScreenGrid(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(10) {
                            ShimmerEffect()
                        }
                    }
                }
                is HomeViewModel.ImagesUiState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HomeScreenGrid(
                            modifier = Modifier.weight(1f)
                        ) {
                            val images = state.images

                            items(images.size) { index ->
                                GlideImage(
                                    model = images[index].previewUrl,
                                    contentDescription = null,
                                    loading = placeholder(R.drawable.ic_placeholder),
                                    failure = placeholder(R.drawable.ic_placeholder),
                                    contentScale = ContentScale.Crop,
                                    colorFilter =
                                    if (images[index].isChecked) ColorFilter.tint(
                                        PictonBlue,
                                        BlendMode.Lighten
                                    )
                                    else null,
                                    modifier = Modifier
                                        .clickable(onClick = { viewModel.itemClicked(index) })
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            onClick = {
                                with(viewModel.getSelectedItems()) {
                                    if (this.size >= 2) {
                                        sharedViewModel.links = this
                                        navController.navigate(ResultScreenRoute)
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.btn_process),
                                color = Color.White
                            )
                        }
                    }
                }
                is HomeViewModel.ImagesUiState.Error -> {
                    ErrorPage()
                }
            }
        }
    }
}

