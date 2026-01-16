package com.example.editprofile

import androidx.lifecycle.viewModelScope
import com.example.domain.model.ProfileImageState
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.ValidateNicknameUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
    @Inject
    constructor(
        private val validateNicknameUseCase: ValidateNicknameUseCase,
        private val userRepository: UserRepository,
    ) : BaseViewModel<EditProfileContract.EditProfileState, EditProfileContract.EditProfileIntent, EditProfileContract.EditProfileSideEffect>(
            EditProfileContract.EditProfileState(),
        ) {
        init {
            initializeUserProfile()
        }

        override fun handleIntent(intent: EditProfileContract.EditProfileIntent) {
            when (intent) {
                is EditProfileContract.EditProfileIntent.OnAlbumImageSelect -> {
                    val imagePath = intent.uri.toString()
                    updateState {
                        copy(
                            profileImagePath = imagePath,
                            profileImageState = ProfileImageState.Update(imagePath),
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
                            profileImagePath = null,
                            isAlbumLauncherBottomSheetVisible = false,
                            profileImageState = ProfileImageState.Delete,
                        )
                    }
                }
                EditProfileContract.EditProfileIntent.OnEditButtonClick -> {
                    editProfile()
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

    private fun editProfile() {
        viewModelScope.launch {
            userRepository
                .updateProfile(
                    nickname = currentState.nickname,
                    profileImageState = currentState.profileImageState,
                ).onSuccess {
                    setSideEffect(EditProfileContract.EditProfileSideEffect.NavigateToBack)
                }.onFailure { }
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

        private fun initializeUserProfile() {
            userRepository
                .getUser()
                .onEach { user ->
                    updateState {
                        copy(
                            profileImagePath = user?.profileImagePath,
                            nickname = user?.nickname ?: "",
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }
