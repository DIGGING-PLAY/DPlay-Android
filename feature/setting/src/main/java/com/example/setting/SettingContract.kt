package com.example.setting

import com.example.ui.base.BaseContract

class SettingContract {
    data class SettingState(
        val isLoading: Boolean = false,
        val isPushNotificationEnabled: Boolean = false,
        val appVersion: String = "1.0.0",
    ) : BaseContract.State

    sealed interface SettingIntent : BaseContract.Intent {
        data class OnMenuClick(
            val type: SettingMenuType,
        ) : SettingIntent

        data object OnLogoutConfirm : SettingIntent

        data object OnWithdrawConfirm : SettingIntent

        data object OnBackIconClick : SettingIntent
    }

    sealed interface SettingSideEffect : BaseContract.SideEffect {
        data class NavigateToWeb(
            val url: String,
        ) : SettingSideEffect

        data object NavigateToLogin : SettingSideEffect

        data object NavigateToBack : SettingSideEffect

        data object ShowLogoutWarningDialog : SettingSideEffect

        data object ShowWithdrawWarningDialog : SettingSideEffect

        data class OpenWebView(val url: String) : SettingSideEffect
    }
}
