package com.mjoudar.withingscompose.data.pixabay_api


import com.mjoudar.withingscompose.BuildConfig
import com.mjoudar.withingscompose.data.pixabay_api.SimpleResponse.Companion.safeApiCall
import com.mjoudar.withingscompose.utils.PIXABAY_PHOTO
import com.mjoudar.withingscompose.utils.PIXABAY_PRETTY_TRUE


class PixabayAPI(private val service: PixabayService) {

    suspend fun getImages(input: String) =
        safeApiCall {
            service.getImages(
                key = BuildConfig.PIXABAY_API_KEY,
                input = input,
                imageType = PIXABAY_PHOTO,
                pretty = PIXABAY_PRETTY_TRUE
            )
        }
}