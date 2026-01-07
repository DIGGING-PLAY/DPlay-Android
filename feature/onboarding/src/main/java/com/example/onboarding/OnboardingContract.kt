package com.example.onboarding

import android.net.Uri
import com.example.common.type.TermType
import com.example.designsystem.component.textfield.type.InputState
import com.example.ui.base.BaseContract

class OnboardingContract {
    data class OnboardingState(
        val kakaoAccessToken: String? = null,
        val agreedTerms: Set<TermType> = emptySet(),
        val nickname: String = "",
        val profileImageUri: Uri? = null,
        val nicknameInputState: InputState = InputState.Default,
        val isAlbumLauncherBottomSheetVisible: Boolean = false,
    ) : BaseContract.State {
        val isTermsScreenNextButtonEnabled: Boolean
            get() = agreedTerms.containsAll(TermType.mandatoryTerms)

        val isAllTermsAgreed: Boolean
            get() = agreedTerms.size == TermType.entries.size

        val isProfileScreenNextButtonEnabled: Boolean
            get() = nicknameInputState is InputState.Success
    }

    sealed interface OnboardingIntent : BaseContract.Intent {
        data class Initialize(val kakaoAccessToken: String) : OnboardingIntent

        data object OnBackButtonClick : OnboardingIntent

        data class OnToggleTerm(
            val term: TermType,
        ) : OnboardingIntent

        data object OnToggleAllTerms : OnboardingIntent

        data object OnTermsScreenNextButtonClick : OnboardingIntent

        data class OnNicknameChanged(
            val input: String,
        ) : OnboardingIntent

        data object OnProfileImageClick : OnboardingIntent

        data object OnAlbumLauncherBottomSheetDismiss : OnboardingIntent

        data object OnDefaultImageSelect : OnboardingIntent

        data object OnAlbumLauncherSelect : OnboardingIntent

        data class OnAlbumImageSelect(
            val uri: Uri?,
        ) : OnboardingIntent

        data object OnProfileScreenNextButtonClick : OnboardingIntent

        data object OnStartButtonClick : OnboardingIntent

        data object OnPermissionConfirmButtonClick : OnboardingIntent

        data class OnNotificationPermissionResult(
            val isGranted: Boolean,
        ) : OnboardingIntent
    }

    sealed interface OnboardingSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : OnboardingSideEffect

        data object NavigateToProfile : OnboardingSideEffect

        data object NavigateToOnboarding : OnboardingSideEffect

        data object NavigateToPermission : OnboardingSideEffect

        data object NavigateToHome : OnboardingSideEffect

        data object ShowPermissionDialog : OnboardingSideEffect

        data object LaunchAlbum : OnboardingSideEffect
    }
}
