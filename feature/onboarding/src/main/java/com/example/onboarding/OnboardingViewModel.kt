package com.example.onboarding

import com.example.designsystem.component.textfield.type.NicknameInputState
import com.example.navigation.Navigator
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BaseViewModel<OnboardingContract.OnboardingState, OnboardingContract.OnboardingIntent, OnboardingContract.OnboardingSideEffect>(
    OnboardingContract.OnboardingState(),
) {
    override fun handleIntent(intent: OnboardingContract.OnboardingIntent) {
        when (intent) {
            OnboardingContract.OnboardingIntent.OnBackButtonClick -> {
                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToBack)
            }

            is OnboardingContract.OnboardingIntent.OnToggleTerm -> {
                updateState {
                    val newAgreedTerms = if (agreedTerms.contains(intent.term)) {
                        agreedTerms - intent.term
                    } else {
                        agreedTerms + intent.term
                    }
                    copy(agreedTerms = newAgreedTerms)
                }
            }

            OnboardingContract.OnboardingIntent.OnToggleAllTerms -> {
                updateState {
                    val newAgreedTerms = if (currentState.isAllTermsAgreed) {
                        emptySet()
                    } else {
                        TermType.entries.toSet()
                    }
                    copy(agreedTerms = newAgreedTerms)
                }
            }

            OnboardingContract.OnboardingIntent.OnTermsScreenNextButtonClick -> {
                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToProfile)
            }


            is OnboardingContract.OnboardingIntent.OnNicknameChanged -> {
                validateAndUpdateNickname(intent.input.trim())
            }

            OnboardingContract.OnboardingIntent.OnProfileScreenNextButtonClick -> {
                // TODO 닉네임 검증(중복 검사, 금칙어 검사)
                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToOnboarding)
            }

            is OnboardingContract.OnboardingIntent.OnAlbumImageSelect -> {
                updateState {
                    copy(
                        profileImageUri = intent.uri,
                        isAlbumLauncherBottomSheetVisible = false
                    )
                }
            }
            OnboardingContract.OnboardingIntent.OnAlbumLauncherBottomSheetDismiss -> {
                updateState {
                    copy(isAlbumLauncherBottomSheetVisible = false)
                }
            }
            OnboardingContract.OnboardingIntent.OnDefaultImageSelect -> {
                updateState {
                    copy(
                        profileImageUri = null,
                        isAlbumLauncherBottomSheetVisible = false
                    )
                }
            }
            OnboardingContract.OnboardingIntent.OnProfileImageClick -> {
                updateState {
                    copy(isAlbumLauncherBottomSheetVisible = true)
                }
            }

            OnboardingContract.OnboardingIntent.OnAlbumLauncherSelect -> {
                // Todo 앨범띄우는 것을 SideEffect로 관리해야할까?
            }

            OnboardingContract.OnboardingIntent.OnStartButtonClick -> {
                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToPermission)
            }

            OnboardingContract.OnboardingIntent.OnPermissionConfirmButtonClick -> {
                setSideEffect(OnboardingContract.OnboardingSideEffect.ShowPermissionDialog)
            }

            is OnboardingContract.OnboardingIntent.OnNotificationPermissionResult -> {
                updateState {
                    copy(
                        isNotificationPermissionGranted = intent.isGranted
                    )
                }
                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToHome)
            }
        }
    }

    private fun validateAndUpdateNickname(nickname: String){
        val inputState = when {
            nickname.length < 2 -> NicknameInputState.Error.NotEnoughLength
            !nickname.matches("^[가-힣a-zA-Z0-9]+\$".toRegex()) -> NicknameInputState.Error.InvalidFormat
            else -> NicknameInputState.Success
        }

        updateState {
            copy(
                nickname = nickname,
                nicknameInputState = inputState
            )
        }
    }
}