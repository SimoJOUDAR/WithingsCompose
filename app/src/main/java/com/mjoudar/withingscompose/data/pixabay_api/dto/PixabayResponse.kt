package com.mjoudar.withingscompose.data.pixabay_api.dto

import com.squareup.moshi.Json

data class PixabayResponse(
    @Json(name = "hits")
    val hits: List<HitsItem>?
)

data class HitsItem(
    @Json(name = "previewURL")
    val previewURL: String = "",
    @Json(name = "webformatURL")
    val webformatURL: String = ""
)