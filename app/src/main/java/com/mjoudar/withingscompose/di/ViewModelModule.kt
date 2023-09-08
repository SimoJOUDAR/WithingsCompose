package com.mjoudar.withingscompose.di

import com.mjoudar.withingscompose.presentation.SharedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }
}