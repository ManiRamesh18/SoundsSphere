package com.example.soundssphere.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel()
{
    private val _splashScreenValue = MutableStateFlow(true)
    val isLoading = _splashScreenValue.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _splashScreenValue.value = false
        }
    }
}