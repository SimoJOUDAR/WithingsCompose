package com.mjoudar.withingscompose.data.repositories

import com.mjoudar.withingscompose.data.pixabay_api.PixabayAPI
import javax.inject.Inject

class ImagesRepository @Inject constructor(private val api: PixabayAPI) {

    suspend fun getImages(input: String) = api.getImages(input)
}