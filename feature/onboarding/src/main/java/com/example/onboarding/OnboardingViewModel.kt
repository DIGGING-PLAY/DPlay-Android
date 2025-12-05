package com.example.onboarding

import com.example.designsystem.component.textfield.type.NicknameInputState
import com.example.navigation.Navigator
import com.example.navigation.Onboarding
import com.example.navigation.OnboardingPermission
import com.example.navigation.OnboardingProfile
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<OnboardingContract.OnboardingState, OnboardingContract.OnboardingIntent, OnboardingContract.OnboardingSideEffect>(
    OnboardingContract.OnboardingState(),
) {
    override fun handleIntent(intent: OnboardingContract.OnboardingIntent) {
        when (intent) {
            OnboardingContract.OnboardingIntent.OnBackButtonClick -> {
                navigator.goBack()
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
                navigator.goTo(OnboardingProfile)
            }


            is OnboardingContract.OnboardingIntent.OnNicknameChanged -> {
                validateAndUpdateNickname(intent.input.trim())
            }

            OnboardingContract.OnboardingIntent.OnProfileScreenNextButtonClick -> {
                // TODO 닉네임 검증(중복 검사, 금칙어 검사)
                navigator.goTo(Onboarding)
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
                navigator.goTo(OnboardingPermission)
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