package com.example.splash

import androidx.lifecycle.ViewModel
import com.example.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    suspend fun onSplashFinished() {
        delay(2000L)
        navigator.navigateToLogin()
    }
}