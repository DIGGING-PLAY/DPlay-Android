package com.example.onboarding

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayButtonBottomSheet
import com.example.designsystem.component.DPlayProfileImageArea
import com.example.designsystem.component.DplayLeftIconTopAppBar
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.TextFieldConstant
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingProfileRoute(
    onboardingNavigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                viewModel.handleIntent(
                    OnboardingContract.OnboardingIntent.OnAlbumImageSelect(uri),
                )
            },
        )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OnboardingContract.OnboardingSideEffect.NavigateToBack -> {
                    onboardingNavigator.navigateToBack()
                }
                OnboardingContract.OnboardingSideEffect.NavigateToOnboarding -> {
                    onboardingNavigator.navigateTo(OnboardingGraph.Onboarding)
                }
                OnboardingContract.OnboardingSideEffect.LaunchAlbum -> {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }
                else -> {}
            }
        }
    }

    OnboardingProfileScreen(
        state = state,
        onNicknameChanged = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnNicknameChanged(it))
        },
        onNextButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnProfileScreenNextButtonClick)
        },
        onProfileImageClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnProfileImageClick)
        },
        onAlbumLauncherBottomSheetDismiss = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnAlbumLauncherBottomSheetDismiss)
        },
        onDefaultImageSelect = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnDefaultImageSelect)
        },
        onBackButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnBackButtonClick)
        },
        onAlbumLauncherSelect = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnAlbumLauncherSelect)
        },
    )
}

@Composable
fun OnboardingProfileScreen(
    state: OnboardingContract.OnboardingState,
    modifier: Modifier = Modifier,
    onNicknameChanged: (String) -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    onAlbumLauncherBottomSheetDismiss: () -> Unit = {},
    onDefaultImageSelect: () -> Unit = {},
    onNextButtonClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onAlbumLauncherSelect: () -> Unit = {},
) {
    BackHandler(enabled = state.isAlbumLauncherBottomSheetVisible, onBack = onAlbumLauncherBottomSheetDismiss)

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(color = DPlayTheme.colors.dplayWhite)
                    .padding(bottom = 16.dp),
        ) {
            DplayLeftIconTopAppBar { onBackButtonClick() }

            Text(
                text = stringResource(com.dplay.onboarding.R.string.profile_screen_title),
                modifier = Modifier.padding(start = 16.dp),
                style = DPlayTheme.typography.titleBold24,
                color = DPlayTheme.colors.dplayBlack,
            )

            Spacer(modifier = Modifier.height(28.dp))

            DPlayProfileImageArea(
                onProfileImageClick = onProfileImageClick,
                profileImagePath = state.profileImagePath,
                modifier =
                    Modifier
                        .size(116.dp)
                        .align(Alignment.CenterHorizontally),
            ) {
                DPlayCircleButton(
                    circleButtonType =
                        CircleButtonType.SmallPlus(
                            R.string.add_profile_image_button_icon_description,
                        ),
                    onClick = { onProfileImageClick() },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            DPlayTextInput(
                value = state.nickname,
                inputState = state.nicknameInputState,
                onValueChange = { onNicknameChanged(it) },
                onFocusChange = {},
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            DPlayLargePinkButton(
                onClick = { onNextButtonClick() },
                label = stringResource(R.string.next_button_label),
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                enabled = state.isProfileScreenNextButtonEnabled,
            )
        }

        // scrim 효과
        if (state.isAlbumLauncherBottomSheetVisible) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = DPlayTheme.colors.dim40)
                        .noRippleClickable { onAlbumLauncherBottomSheetDismiss() },
            )
        }

        AnimatedVisibility(
            visible = state.isAlbumLauncherBottomSheetVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter =
                slideInVertically(
                    initialOffsetY = { it },
                ),
            exit =
                slideOutVertically(
                    targetOffsetY = { it },
                ),
        ) {
            DPlayButtonBottomSheet(
                mainText = stringResource(R.string.launch_album_bottomsheet_main_text),
                subText = stringResource(R.string.launch_album_bottomsheet_sub_text),
                mainOnClick = { onAlbumLauncherSelect() },
                subOnClick = { onDefaultImageSelect() },
                modifier = Modifier.noRippleClickable(),
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingProfileScreenPreview() {
    DPlayTheme {
        OnboardingProfileScreen(
            state = OnboardingContract.OnboardingState(),
        )
    }
}
