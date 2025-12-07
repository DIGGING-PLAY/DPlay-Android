package com.example.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.Navigator
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<SplashContract.SplashState, SplashContract.SplashIntent, SplashContract.SplashSideEffect>(
    SplashContract.SplashState()
) {
    override fun handleIntent(intent: SplashContract.SplashIntent) {
        when (intent) {
            SplashContract.SplashIntent.OnSplashScreenStart -> {
                viewModelScope.launch {
                    onSplashStart()
                }
            }
        }

    }
    suspend fun onSplashStart() {
        delay(2000L)
        setSideEffect(SplashContract.SplashSideEffect.NavigateToLogin)
    }
}