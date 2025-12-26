package com.example.setting

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor() : BaseViewModel<SettingContract.SettingState, SettingContract.SettingIntent, SettingContract.SettingSideEffect>(
        SettingContract.SettingState(),
    ){
        override fun handleIntent(intent: SettingContract.SettingIntent) {
            when(intent){
                SettingContract.SettingIntent.OnAnnouncementClick -> {
                    // 공지사항 노션 링크로 연결
                }
                SettingContract.SettingIntent.OnInquiryClick -> {
                    // 문의/제안하기 구글폼 링크로 연결
                }
                SettingContract.SettingIntent.OnTermsClick -> {
                    // 서비스 이용약관 노션 링크로 연결
                }
                SettingContract.SettingIntent.OnPrivacyClick -> {
                    // 개인정보 처리방침 노션 링크로 연결
                }
                SettingContract.SettingIntent.OnBackIconClick -> {
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToBack)
                }
                is SettingContract.SettingIntent.OnPushNotificationToggle -> {
                    updateState {
                        copy(
                            isPushNotificationEnabled = !intent.isEnabled
                        )
                    }
                }
                SettingContract.SettingIntent.OnLogoutClick -> TODO()
                SettingContract.SettingIntent.OnLogoutConfirm -> TODO()
                SettingContract.SettingIntent.OnWithdrawClick -> TODO()
                SettingContract.SettingIntent.OnWithdrawConfirm -> TODO()
            }
        }
    }