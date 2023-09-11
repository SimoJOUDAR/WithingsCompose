package com.mjoudar.withingscompose.utils

import com.mjoudar.withingscompose.data.pixabay_api.dto.HitsItem
import com.mjoudar.withingscompose.data.pixabay_api.dto.PixabayResponse
import com.mjoudar.withingscompose.domain.models.ImageInfo


fun HitsItem.toImageInfo() =
    ImageInfo(
        previewUrl = previewURL,
        displayUrl = webformatURL
    )

fun PixabayResponse.toImageInfo() =
    hits?.map { it.toImageInfo() }?.toMutableList() ?: mutableListOf()