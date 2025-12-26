package com.example.setting

import com.dplay.setting.BuildConfig
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
                SettingContract.SettingIntent.Initialize -> {
                    // 앱 버전, 알림 권한 초기화
                }
                is SettingContract.SettingIntent.OnMenuClick -> {
                    handleMenuClick(intent.type)
                }
                SettingContract.SettingIntent.OnBackIconClick -> {
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToBack)
                }
                SettingContract.SettingIntent.OnLogoutConfirm -> {
                    // 로그아웃 api
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                }
                SettingContract.SettingIntent.OnWithdrawConfirm -> {
                    // 회원탈퇴 api
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                }
            }
        }

        private fun handleMenuClick(type: SettingMenuType) {
            when (type) {
                SettingMenuType.PUSH_NOTIFICATION -> {
                    updateState {
                        copy(
                            isPushNotificationEnabled = !this.isPushNotificationEnabled
                        )
                    }
                }
                SettingMenuType.ANNOUNCEMENT -> {
                    // 공지사항 노션 링크로 연결
                }
                SettingMenuType.INQUIRY -> {
                    // 문의/제안하기 구글폼 링크로 연결
                }
                SettingMenuType.TERMS -> {
                    // 서비스 이용약관 노션 링크로 연결
                }
                SettingMenuType.PRIVACY -> {
                    // 개인정보 처리방침 노션 링크로 연결
                }
                SettingMenuType.LOGOUT -> {
                    setSideEffect(SettingContract.SettingSideEffect.ShowDialog)
                }
                SettingMenuType.WITHDRAW -> {
                    setSideEffect(SettingContract.SettingSideEffect.ShowDialog)
                }
                SettingMenuType.VERSION -> { /* 동작없음 */ }
            }
        }
    }