package com.example.setting

import androidx.lifecycle.viewModelScope
import com.example.common.constant.Url
import com.example.domain.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : BaseViewModel<SettingContract.SettingState, SettingContract.SettingIntent, SettingContract.SettingSideEffect>(
            SettingContract.SettingState(),
        ) {
        override fun handleIntent(intent: SettingContract.SettingIntent) {
            when (intent) {
                is SettingContract.SettingIntent.OnMenuClick -> {
                    handleMenuClick(intent.type)
                }
                SettingContract.SettingIntent.OnBackIconClick -> {
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToBack)
                }
                SettingContract.SettingIntent.OnLogoutConfirm -> {
                    logout()
                }
                SettingContract.SettingIntent.OnWithdrawConfirm -> {
                    withdraw()
                }
                is SettingContract.SettingIntent.UpdateNotificationPermission -> {
                    updateState {
                        copy(
                            isPushNotificationEnabled = intent.isGranted,
                        )
                    }
                }
            }
        }

        private fun withdraw() {
            viewModelScope.launch {
                authRepository
                    .withdraw()
                    .onSuccess {
                        setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                    }.onFailure {
                    }
            }
        }

        private fun logout() {
            viewModelScope.launch {
                authRepository
                    .logout()
                    .onSuccess {
                        setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                    }.onFailure {
                    }
            }
        }

        private fun handleMenuClick(type: SettingMenuType) {
            when (type) {
                SettingMenuType.PUSH_NOTIFICATION -> {
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToNotificationSetting)
                }
                SettingMenuType.ANNOUNCEMENT -> {
                    setSideEffect(SettingContract.SettingSideEffect.OpenWebView(type.url ?: Url.ERROR))
                }
                SettingMenuType.INQUIRY -> {
                    setSideEffect(SettingContract.SettingSideEffect.OpenWebView(type.url ?: Url.ERROR))
                }
                SettingMenuType.TERMS -> {
                    setSideEffect(SettingContract.SettingSideEffect.OpenWebView(type.url ?: Url.ERROR))
                }
                SettingMenuType.PRIVACY -> {
                    setSideEffect(SettingContract.SettingSideEffect.OpenWebView(type.url ?: Url.ERROR))
                }
                SettingMenuType.LOGOUT -> {
                    setSideEffect(SettingContract.SettingSideEffect.ShowLogoutWarningDialog)
                }
                SettingMenuType.WITHDRAW -> {
                    setSideEffect(SettingContract.SettingSideEffect.ShowWithdrawWarningDialog)
                }
                SettingMenuType.VERSION -> { /* 동작없음 */ }
            }
        }
    }
