package com.example.editprofile

import android.net.Uri
import com.example.designsystem.component.textfield.type.InputState
import com.example.ui.base.BaseContract

class EditProfileContract {
    data class EditProfileState(
        val nickname: String = "",
        val nicknameInputState: InputState = InputState.Default,
        val profileImagePath: String? = null,
        val isAlbumLauncherBottomSheetVisible: Boolean = false,
    ) : BaseContract.State {
        val isEditButtonEnabled: Boolean
            get() = nicknameInputState is InputState.Success
    }

    sealed interface EditProfileIntent : BaseContract.Intent {
        data object OnBackButtonClick : EditProfileIntent

        data class OnNicknameChanged(
            val input: String,
        ) : EditProfileIntent

        data object OnProfileImageClick : EditProfileIntent

        data object OnAlbumLauncherBottomSheetDismiss : EditProfileIntent

        data object OnDefaultImageSelect : EditProfileIntent

        data object OnAlbumLauncherSelect : EditProfileIntent

        data class OnAlbumImageSelect(
            val uri: Uri?,
        ) : EditProfileIntent

        data object OnEditButtonClick : EditProfileIntent
    }

    sealed interface EditProfileSideEffect : BaseContract.SideEffect {
        data object LaunchAlbum : EditProfileSideEffect

        data object NavigateToBack : EditProfileSideEffect
    }
}
