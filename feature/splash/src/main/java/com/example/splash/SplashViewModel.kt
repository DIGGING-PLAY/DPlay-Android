package com.example.splash

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : BaseViewModel<SplashContract.SplashState, SplashContract.SplashIntent, SplashContract.SplashSideEffect>(
            SplashContract.SplashState(),
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

        private suspend fun checkAutoLogin() {
            runCatching {
                val accessToken = userRepository.getAccessToken().firstOrNull()
                val refreshToken = userRepository.getRefreshToken().firstOrNull()
                accessToken to refreshToken
            }.onSuccess { (accessToken, refreshToken) ->
                val destination =
                    if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                        SplashContract.SplashSideEffect.NavigateToLogin
                    } else {
                        SplashContract.SplashSideEffect.NavigateToHome
                    }
                setSideEffect(destination)
            }.onFailure {
                setSideEffect(SplashContract.SplashSideEffect.NavigateToLogin)
            }
        }

        private suspend fun onSplashStart() {
            delay(SPLASH_DELAY_MS)
            checkAutoLogin()
        }

        companion object {
            private const val SPLASH_DELAY_MS = 1000L
        }
    }
