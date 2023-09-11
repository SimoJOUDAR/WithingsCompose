package com.mjoudar.withingscompose.presentation.ui.components

import android.net.Uri
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.mjoudar.withingscompose.R
import com.mjoudar.withingscompose.presentation.ui.screens.result_screen.ResultViewModel
import com.mjoudar.withingscompose.presentation.ui.theme.ErrorRed
import com.mjoudar.withingscompose.presentation.ui.theme.LatoTypograpgy
import com.mjoudar.withingscompose.presentation.ui.theme.LightGray
import com.mjoudar.withingscompose.presentation.ui.theme.MediumGrey
import java.io.File
import kotlin.random.Random


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(16.dp)
            )
            .padding(4.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search),
                        color = if (isSystemInDarkTheme()) LightGray else Color.Gray
                    )
                }
                innerTextField()
            }
        )

        IconButton(
            onClick = {
                keyboardController?.hide()
                onSearch(text)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.search)
            )
        }
    }
}

@Composable
fun HomeScreenGrid(modifier: Modifier,content: LazyStaggeredGridScope.() -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        content()
    }
}

@Composable
fun ShimmerEffect() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Random.nextInt(100, 300).dp)
            .clip(RoundedCornerShape(12.dp))
            .shimmerEffect()
    )
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = "startOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                LightGray,
                MediumGrey,
                LightGray,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun ErrorPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(2f / 3)
                .fillMaxHeight(2f / 3)
                .clip(RoundedCornerShape(16.dp))
                .background(ErrorRed),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.unidentified_error_message),
                style = LatoTypograpgy.titleSmall,
                color = Color.White
            )
        }
    }
}

@Composable
fun Processing() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(R.string.processing),
            color = MaterialTheme.colorScheme.primary,
            style = LatoTypograpgy.titleLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            modifier = Modifier
                .weight(2f, false)
                .clip(RoundedCornerShape(16.dp)),
            painter = rememberAsyncImagePainter(
                model = R.drawable.ic_processing,
                imageLoader = imageLoader,
                contentScale = ContentScale.Fit
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DisplayGif(fileName: String) {
    val uri = Uri.fromFile(File(LocalContext.current.getExternalFilesDir(null), fileName))
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()


    Image(
        painter = rememberAsyncImagePainter(uri, imageLoader),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DisplayResult(state: ResultViewModel.GifUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            ResultViewModel.GifUiState.Loading -> Processing()
            is ResultViewModel.GifUiState.Success -> DisplayGif(state.fileName)
            is ResultViewModel.GifUiState.Error -> ErrorPage()
        }
    }
}





