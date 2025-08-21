package com.example.recipebox.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel()  {
    private  val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished = _isSplashFinished
    init {
        viewModelScope.launch {
            delay(2000)
            _isSplashFinished.value = true
        }
    }
}