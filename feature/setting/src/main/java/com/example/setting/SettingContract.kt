package com.example.setting

import com.example.ui.base.BaseContract


class SettingContract{
    data class SettingState(
        val isLoading: Boolean = false,
        val isPushNotificationEnabled: Boolean = false,
        val appVersion: String = "1.0.0",
    ) : BaseContract.State

    sealed interface SettingIntent{
        data class OnPushNotificationToggle(val isEnabled: Boolean) : SettingIntent
        data object OnAnnouncementClick : SettingIntent
        data object OnInquiryClick : SettingIntent
        data object OnTermsClick : SettingIntent
        data object OnPrivacyClick : SettingIntent
        data object OnLogoutClick : SettingIntent
        data object OnLogoutConfirm : SettingIntent
        data object OnWithdrawClick : SettingIntent
        data object OnWithdrawConfirm : SettingIntent
        data object OnBackIconClick : SettingIntent
        data object OnDismissDialog : SettingIntent
    }

    sealed interface SettingSideEffect{
        data class NavigateToWeb(val url: String) : SettingSideEffect

        data object NavigateToLogin : SettingSideEffect

        data object ShowDialog : SettingSideEffect
    }
}