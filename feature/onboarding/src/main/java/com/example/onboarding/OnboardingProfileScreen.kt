package com.example.onboarding

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayButtonBottomSheet
import com.example.designsystem.component.DplayLeftIconTopAppBar
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.TextFieldConstant
import com.example.designsystem.util.noRippleClickable

@Composable
fun OnboardingProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
        onAlbumImageSelect = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnAlbumImageSelect(it))
        },
        onBackButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnBackButtonClick)
        }
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
    onAlbumImageSelect: (String?) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
){
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onAlbumImageSelect(uri?.toString()) }
    )

    BackHandler(enabled = state.isAlbumLauncherBottomSheetVisible, onBack = onAlbumLauncherBottomSheetDismiss)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
                .padding(bottom = 16.dp)
        ) {
            DplayLeftIconTopAppBar { onBackButtonClick() }

            Text(
                text = "디플레이에서 사용할\n프로필을 완성해주세요",
                modifier = Modifier.padding(start = 16.dp),
                style = DPlayTheme.typography.titleBold24,
                color = DPlayTheme.colors.dplayBlack
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .noRippleClickable(
                        onClick = { onProfileImageClick() }
                    )
            ) {
                AsyncImage(
                    model = state.profileImageUri?.toUri() ?: R.drawable.img_profile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(116.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = DPlayTheme.colors.gray200,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                DPlayCircleButton(
                    circleButtonType =
                        CircleButtonType.SmallPlus(
                            R.string.add_profile_image_button_icon_description,
                        ),
                    onClick = { onProfileImageClick() },
                    modifier = Modifier.align(Alignment.BottomEnd)
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
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            DPlayLargePinkButton(
                onClick = { onNextButtonClick() },
                label = stringResource(R.string.next_button_label),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                enabled = state.isProfileScreenNextButtonEnabled
            )
        }

        // scrim 효과
        if (state.isAlbumLauncherBottomSheetVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = DPlayTheme.colors.dim40)
                    .noRippleClickable{ onAlbumLauncherBottomSheetDismiss() }
            )
        }

        AnimatedVisibility(
            visible = state.isAlbumLauncherBottomSheetVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { it }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }
            )
        ) {
            DPlayButtonBottomSheet(
                mainText = "앨범에서 선택하기",
                subText = "기본 이미지로 변경하기",
                mainOnClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                subOnClick = { onDefaultImageSelect() },
                modifier = Modifier.noRippleClickable()
            )
        }
    }

}

@Preview
@Composable
private fun OnboardingProfileScreenPreview() {
    DPlayTheme {
        OnboardingProfileScreen(
            state = OnboardingContract.OnboardingState()
        )
    }
}