package com.example.ui.controller

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BottomNavigationController {
    var bottomNavigationVisible by mutableStateOf(true)
        private set

    fun show() {
        bottomNavigationVisible = true
    }

    fun hide() {
        bottomNavigationVisible = false
    }
}

val LocalBottomNavigationController = compositionLocalOf<BottomNavigationController> {
    error("BottomNavigationController not provided")
}