package com.spidex.safe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    private val _currentScreen = mutableStateOf(1)
    val currentScreen = _currentScreen

    fun setCurrentScreen(screen: Int) {
        _currentScreen.value = screen
    }
}