package com.example.editprofile

import com.example.domain.usecase.ValidateNicknameUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
    @Inject
    constructor(
        private val validateNicknameUseCase: ValidateNicknameUseCase,
    ) : BaseViewModel<EditProfileContract.EditProfileState, EditProfileContract.EditProfileIntent, EditProfileContract.EditProfileSideEffect>(
            EditProfileContract.EditProfileState(),
        ) {
        override fun handleIntent(intent: EditProfileContract.EditProfileIntent) {
            when (intent) {
                is EditProfileContract.EditProfileIntent.OnAlbumImageSelect -> {
                    updateState {
                        copy(
                            profileImageUri = intent.uri,
                            isAlbumLauncherBottomSheetVisible = false,
                        )
                    }
                }
                EditProfileContract.EditProfileIntent.OnAlbumLauncherBottomSheetDismiss -> {
                    updateState {
                        copy(
                            isAlbumLauncherBottomSheetVisible = false,
                        )
                    }
                }
                EditProfileContract.EditProfileIntent.OnAlbumLauncherSelect -> {
                    setSideEffect(EditProfileContract.EditProfileSideEffect.LaunchAlbum)
                }
                EditProfileContract.EditProfileIntent.OnBackButtonClick -> {
                    setSideEffect(EditProfileContract.EditProfileSideEffect.NavigateToBack)
                }
                EditProfileContract.EditProfileIntent.OnDefaultImageSelect -> {
                    updateState {
                        copy(
                            profileImageUri = null,
                            isAlbumLauncherBottomSheetVisible = false,
                        )
                    }
                }
                EditProfileContract.EditProfileIntent.OnEditButtonClick -> {
                    setSideEffect(EditProfileContract.EditProfileSideEffect.NavigateToBack)
                }
                is EditProfileContract.EditProfileIntent.OnNicknameChanged -> {
                    validateAndUpdateNickname(intent.input.trim())
                }
                EditProfileContract.EditProfileIntent.OnProfileImageClick -> {
                    updateState {
                        copy(isAlbumLauncherBottomSheetVisible = true)
                    }
                }
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
