package com.example.login

import com.example.ui.base.BaseContract

class LoginContract {
    data class LoginState(
        val loading: Boolean = false,
    ) : BaseContract.State

    sealed interface LoginIntent : BaseContract.Intent {
        data object OnKakaoLogin : LoginIntent
    }

    sealed interface LoginSideEffect : BaseContract.SideEffect
}