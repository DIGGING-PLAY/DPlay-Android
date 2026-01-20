package com.example.onboarding

import androidx.lifecycle.viewModelScope
import com.example.common.type.TermType
import com.example.domain.model.NicknameValidationResult
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.ValidateNicknameUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        private val validateNicknameUseCase: ValidateNicknameUseCase,
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
    ) : BaseViewModel<OnboardingContract.OnboardingState, OnboardingContract.OnboardingIntent, OnboardingContract.OnboardingSideEffect>(
            OnboardingContract.OnboardingState(),
        ) {
        override fun handleIntent(intent: OnboardingContract.OnboardingIntent) {
            when (intent) {
                is OnboardingContract.OnboardingIntent.Initialize -> {
                    updateState {
                        copy(
                            kakaoAccessToken = intent.kakaoAccessToken,
                        )
                    }
                }

                OnboardingContract.OnboardingIntent.OnBackButtonClick -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToBack)
                }

                is OnboardingContract.OnboardingIntent.OnToggleTerm -> {
                    toggleEachTerm(intent.term)
                }

                OnboardingContract.OnboardingIntent.OnToggleAllTerms -> {
                    toggleAllTerms()
                }

                is OnboardingContract.OnboardingIntent.OnTermsArrowClick -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.OpenWebView(intent.term.url))
                }

                OnboardingContract.OnboardingIntent.OnTermsScreenNextButtonClick -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToProfile)
                }

                is OnboardingContract.OnboardingIntent.OnNicknameChanged -> {
                    validateAndUpdateNickname(intent.input.trim())
                }

                OnboardingContract.OnboardingIntent.OnProfileScreenNextButtonClick -> {
                    signUpWithKakao()
                }

                is OnboardingContract.OnboardingIntent.OnAlbumImageSelect -> {
                    updateState {
                        copy(
                            profileImagePath = intent.uri.toString(),
                            isAlbumLauncherBottomSheetVisible = false,
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
                            profileImagePath = null,
                            isAlbumLauncherBottomSheetVisible = false,
                        )
                    }
                }
                OnboardingContract.OnboardingIntent.OnProfileImageClick -> {
                    updateState {
                        copy(isAlbumLauncherBottomSheetVisible = true)
                    }
                }

                OnboardingContract.OnboardingIntent.OnAlbumLauncherSelect -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.LaunchAlbum)
                }

                OnboardingContract.OnboardingIntent.OnStartButtonClick -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToPermission)
                }

                OnboardingContract.OnboardingIntent.OnPermissionConfirmButtonClick -> {
                    setSideEffect(OnboardingContract.OnboardingSideEffect.ShowPermissionDialog)
                }

                is OnboardingContract.OnboardingIntent.OnNotificationPermissionResult -> {
                    viewModelScope.launch {
                        userRepository
                            .updateNotificationEnabled(intent.isGranted)
                            .onSuccess {
                                setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToHome)
                            }.onFailure {
                            }
                    }
                }
            }
        }

        private fun signUpWithKakao() {
            viewModelScope.launch {
                authRepository
                    .signupWithKakao(
                        kakaoAccessToken = currentState.kakaoAccessToken,
                        profileImage = currentState.profileImagePath,
                        nickname = currentState.nickname,
                    ).onSuccess { validationResult ->
                        if (validationResult is NicknameValidationResult.Error.Duplicated) {
                            updateState {
                                copy(
                                    nicknameInputState = NicknameValidationResult.Error.Duplicated.toUiState()
                                )
                            }
                        } else if(validationResult is NicknameValidationResult.Success){
                            setSideEffect(OnboardingContract.OnboardingSideEffect.NavigateToOnboarding)
                        }
                    }.onFailure {
                    }
            }
        }

        private fun toggleAllTerms() {
            updateState {
                val newAgreedTerms =
                    if (currentState.isAllTermsAgreed) {
                        emptySet()
                    } else {
                        TermType.entries.toSet()
                    }
                copy(agreedTerms = newAgreedTerms)
            }
        }

        private fun toggleEachTerm(term: TermType) {
            updateState {
                val newAgreedTerms =
                    if (agreedTerms.contains(term)) {
                        agreedTerms - term
                    } else {
                        agreedTerms + term
                    }
                copy(agreedTerms = newAgreedTerms)
            }
        }

        private fun validateAndUpdateNickname(nickname: String) {
            val validationResult = validateNicknameUseCase(nickname)
            val inputState = validationResult.toUiState()
            updateState {
                copy(
                    nickname = nickname,
                    nicknameInputState = inputState,
                )
            }
        }
    }
