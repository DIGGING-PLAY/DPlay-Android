package com.example.editprofile

import com.example.common.constant.Regex
import com.example.designsystem.component.textfield.type.NicknameInputState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
@Inject constructor() : BaseViewModel<EditProfileContract.EditProfileState, EditProfileContract.EditProfileIntent, EditProfileContract.EditProfileSideEffect>(
    EditProfileContract.EditProfileState()
) {
    override fun handleIntent(intent: EditProfileContract.EditProfileIntent) {
        when(intent){
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
                setSideEffect(EditProfileContract.EditProfileSideEffect.NavigateToMyPage)
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


    // 공통 로직으로 추출 필요
    private fun validateAndUpdateNickname(nickname: String) {
        val inputState =
            when {
                nickname.length < 2 -> NicknameInputState.Error.NotEnoughLength
                !nickname.matches(Regex.NICKNAME_REGEX) -> NicknameInputState.Error.InvalidFormat
                else -> NicknameInputState.Success
            }

        updateState {
            copy(
                nickname = nickname,
                nicknameInputState = inputState,
            )
        }
    }
}