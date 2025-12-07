package com.example.login

import com.example.navigation.Navigator
import com.example.navigation.OnboardingTerms
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor() : BaseViewModel<LoginContract.LoginState, LoginContract.LoginIntent, LoginContract.LoginSideEffect>(
    LoginContract.LoginState(),
) {
    override fun handleIntent(intent: LoginContract.LoginIntent) {
        when (intent) {
            LoginContract.LoginIntent.OnKakaoLogin -> {
                setSideEffect(LoginContract.LoginSideEffect.NavigateToOnboarding)
            }
        }
    }
}