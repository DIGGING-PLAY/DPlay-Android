package com.example.setting

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
    ) : BaseViewModel<SettingContract.SettingState, SettingContract.SettingIntent, SettingContract.SettingSideEffect>(
            SettingContract.SettingState(),
        ) {

        init {
            initializeNotificationEnabled()
        }

        override fun handleIntent(intent: SettingContract.SettingIntent) {
            when (intent) {
                is SettingContract.SettingIntent.OnMenuClick -> {
                    handleMenuClick(intent.type)
                }
                SettingContract.SettingIntent.OnBackIconClick -> {
                    setSideEffect(SettingContract.SettingSideEffect.NavigateToBack)
                }
                SettingContract.SettingIntent.OnLogoutConfirm -> {
                    viewModelScope.launch {
                        authRepository
                            .logout()
                            .onSuccess {
                                setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                            }.onFailure {
                            }
                    }
                }
                SettingContract.SettingIntent.OnWithdrawConfirm -> {
                    viewModelScope.launch {
                        authRepository
                            .withdraw()
                            .onSuccess {
                                setSideEffect(SettingContract.SettingSideEffect.NavigateToLogin)
                            }.onFailure {
                            }
                    }
                }
            }
        }

        private fun handleMenuClick(type: SettingMenuType) {
            when (type) {
                SettingMenuType.PUSH_NOTIFICATION -> {
                    updateState {
                        copy(
                            isPushNotificationEnabled = !this.isPushNotificationEnabled,
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
                    setSideEffect(SettingContract.SettingSideEffect.ShowLogoutWarningDialog)
                }
                SettingMenuType.WITHDRAW -> {
                    setSideEffect(SettingContract.SettingSideEffect.ShowWithdrawWarningDialog)
                }
                SettingMenuType.VERSION -> { /* 동작없음 */ }
            }
        }

        private fun initializeNotificationEnabled(){
            viewModelScope.launch {
                userRepository.getNotificationEnabled()
                    .onSuccess {
                        updateState {
                            copy(
                                isPushNotificationEnabled = it
                            )
                        }
                    }.onFailure {

                    }
            }
        }
    }
