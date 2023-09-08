package com.mjoudar.withingscompose.domain.models

data class ImageInfo(
    val previewUrl: String,
    val displayUrl: String,
    val isChecked: Boolean = false
)