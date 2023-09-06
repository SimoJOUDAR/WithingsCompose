package com.mjoudar.withingscompose.di

import com.mjoudar.withingscompose.data.pixabay_api.PixabayAPI
import com.mjoudar.withingscompose.data.pixabay_api.PixabayService
import com.mjoudar.withingscompose.utils.PIXABAY_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(PIXABAY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providePixabayService(retrofit: Retrofit): PixabayService =
        retrofit.create(PixabayService::class.java)

    @Singleton
    @Provides
    fun providePixabayApi(service: PixabayService): PixabayAPI = PixabayAPI(service)
}