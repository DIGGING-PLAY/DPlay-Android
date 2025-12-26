package com.example.editprofile

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayButtonBottomSheet
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.TextFieldConstant
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.MyPage
import com.example.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                viewModel.handleIntent(
                    EditProfileContract.EditProfileIntent.OnAlbumImageSelect(uri),
                )
            },
        )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                EditProfileContract.EditProfileSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                EditProfileContract.EditProfileSideEffect.NavigateToMyPage -> {
                    navigator.clearAndNavigateTo(MyPage)
                }
                EditProfileContract.EditProfileSideEffect.LaunchAlbum -> {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }
            }
        }
    }

    EditProfileScreen(
        state = state,
        onNicknameChanged = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnNicknameChanged(it))
        },
        onEditButtonClick = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnEditButtonClick)
        },
        onProfileImageClick = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnProfileImageClick)
        },
        onAlbumLauncherBottomSheetDismiss = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnAlbumLauncherBottomSheetDismiss)
        },
        onDefaultImageSelect = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnDefaultImageSelect)
        },
        onBackButtonClick = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnBackButtonClick)
        },
        onAlbumLauncherSelect = {
            viewModel.handleIntent(EditProfileContract.EditProfileIntent.OnAlbumLauncherSelect)
        },
    )
}

@Composable
fun EditProfileScreen(
    state: EditProfileContract.EditProfileState,
    modifier: Modifier = Modifier,
    onNicknameChanged: (String) -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    onAlbumLauncherBottomSheetDismiss: () -> Unit = {},
    onDefaultImageSelect: () -> Unit = {},
    onEditButtonClick: () -> Unit = {},
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
            DplayLeftIconTitleTopAppBar(
                title = "프로필 수정"
            ) { onBackButtonClick() }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .noRippleClickable(
                            onClick = { onProfileImageClick() },
                        ),
            ) {
                AsyncImage(
                    model = state.profileImageUri ?: R.drawable.img_profile,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(116.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = DPlayTheme.colors.gray200,
                                shape = CircleShape,
                            ),
                    contentScale = ContentScale.Crop,
                )

                DPlayCircleButton(
                    circleButtonType =
                        CircleButtonType.SmallPlus(
                            R.string.add_profile_image_button_icon_description,
                        ),
                    onClick = { onProfileImageClick() },
                    modifier = Modifier.align(Alignment.BottomEnd),
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
                onClick = { onEditButtonClick() },
                label = stringResource(R.string.modify_button_label),
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                enabled = state.isEditButtonEnabled,
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
                mainText = "앨범에서 선택하기",
                subText = "기본 이미지로 변경하기",
                mainOnClick = { onAlbumLauncherSelect() },
                subOnClick = { onDefaultImageSelect() },
                modifier = Modifier.noRippleClickable(),
            )
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    DPlayTheme {
        EditProfileScreen(
            state = EditProfileContract.EditProfileState(),
        )
    }
}