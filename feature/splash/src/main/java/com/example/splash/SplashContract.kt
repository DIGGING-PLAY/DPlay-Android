package com.example.splash

import com.example.ui.base.BaseContract

class SplashContract {
    data class SplashState(
        val isLoading: Boolean = true,
    ) : BaseContract.State

    sealed interface SplashIntent : BaseContract.Intent {
        data object OnSplashScreenStart : SplashIntent
    }

    sealed interface SplashSideEffect : BaseContract.SideEffect {
        data object NavigateToLogin : SplashSideEffect

        data object NavigateToHome : SplashSideEffect
    }
}
