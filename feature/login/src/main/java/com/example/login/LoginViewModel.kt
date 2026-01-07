package com.example.login

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : BaseViewModel<LoginContract.LoginState, LoginContract.LoginIntent, LoginContract.LoginSideEffect>(
            LoginContract.LoginState(),
        ) {
        override fun handleIntent(intent: LoginContract.LoginIntent) {
            when (intent) {
                LoginContract.LoginIntent.OnKakaoLogin -> {
                    kakaoLogin()
                }
            }
        }

    private fun kakaoLogin() {
        viewModelScope.launch {
            authRepository
                .kakaoLogin()
                .onSuccess { data ->
                    if(data.isEmpty()) setSideEffect(LoginContract.LoginSideEffect.NavigateToHome)
                    else setSideEffect(LoginContract.LoginSideEffect.NavigateToOnboarding(data))
                }.onFailure {
                    // 카카오 로그인 실패 처리
                }
        }
    }
}
