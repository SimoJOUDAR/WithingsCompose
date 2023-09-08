package com.mjoudar.withingscompose.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor() : ViewModel() {
    var links = arrayOf<String>()
}