package com.example.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayButtonBottomSheet
import com.example.designsystem.component.DPlayMusicDiscItem
import com.example.designsystem.component.DPlayReportBottomSheet
import com.example.designsystem.component.DplayDualIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayBookmarkButton
import com.example.designsystem.component.button.DPlayLikeButton
import com.example.designsystem.component.button.DPlayStreamingButton
import com.example.designsystem.component.chip.DPlayChip
import com.example.designsystem.component.chip.type.DPlayChipType
import com.example.designsystem.component.snackbar.LocalShowSnackBar
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.designsystem.util.roundedBackgroundWithPadding
import com.example.domain.model.BADGE
import com.example.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    postId: Long,
    navigator: Navigator,
    viewModel: DetailViewModel = hiltViewModel(),
    badge: BADGE? = null,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showSnackBar = LocalShowSnackBar.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DetailContract.DetailIntent.LoadData(postId = postId, badge = badge))
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is DetailContract.DetailSideEffect.ShowSnackBar -> {
                    showSnackBar(it.snackBarType, it.action)
                }

                is DetailContract.DetailSideEffect.NavigateBackStack -> {
                    navigator.navigateToBack()
                }

                is DetailContract.DetailSideEffect.NavigateToWriterProfile -> {
                    // TODO
                }

                is DetailContract.DetailSideEffect.NavigateToMyPage -> {
                    // TODO
                }
            }
        }
    }

    DetailScreen(
        state = uiState,
        onTopAppBarLeftIconClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnBackButtonClick)
        },
        onTopAppBarRightIconClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnMeatBallsClick)
        },
        onBookmarkClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnBookmarkClick)
        },
        onStreamClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnStreamClick)
        },
        onLikeClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnLikeClick)
        },
        onWriterProfileClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnWriterProfileClick)
        },
        changeBottomSheetVisible = { visible ->
            viewModel.handleIntent(DetailContract.DetailIntent.ChangeBottomSheetVisible(visible))
        },
        onDeleteClick = {
            viewModel.handleIntent(DetailContract.DetailIntent.OnDeleteClick)
        },
    )
}

@Composable
private fun DetailScreen(
    onTopAppBarLeftIconClick: () -> Unit,
    onTopAppBarRightIconClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onStreamClick: () -> Unit,
    onLikeClick: () -> Unit,
    onWriterProfileClick: () -> Unit,
    onDeleteClick: () -> Unit,
    changeBottomSheetVisible: (Boolean) -> Unit,
    state: DetailContract.DetailState = DetailContract.DetailState(),
) {
    val color = DPlayTheme.colors
    val typography = DPlayTheme.typography
    val horizontalModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    Box {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier =
                    Modifier.blur(
                        radius = 20.dp,
                    ),
            ) {
                AsyncImage(
                    model = state.track.coverImg,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .offset(y = (-80).dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Column {
                DplayDualIconTitleTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = state.date,
                    leftIconRes = R.drawable.ic_arrow_left_16,
                    rightIconRes = R.drawable.ic_more_24,
                    onLeftClick = onTopAppBarLeftIconClick,
                    onRightClick = onTopAppBarRightIconClick,
                )
                Spacer(modifier = Modifier.height(24.dp))

                Box(Modifier.padding(horizontal = 97.dp)) {
                    DPlayMusicDiscItem(
                        imageUrl = state.track.coverImg,
                        isStreaming = state.streamingTrackId == state.track.trackId,
                    )
                    DPlayBookmarkButton(
                        isMarked = state.isScrapped,
                        onClick = onBookmarkClick,
                        modifier = Modifier.align(Alignment.TopEnd),
                    )
                    state.badge?.let { badge ->
                        val chipType =
                            when (badge) {
                                BADGE.BEST -> DPlayChipType.BEST
                                BADGE.EDITOR -> DPlayChipType.EDITOR
                                BADGE.NEW -> DPlayChipType.NEW
                            }
                        DPlayChip(
                            type = chipType,
                            modifier = Modifier.align(Alignment.BottomCenter),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = state.track.songTitle,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = typography.bodyBold20,
                    color = color.dplayBlack,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.track.artistName,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = typography.bodySemi14,
                    color = color.gray400,
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = horizontalModifier) {
                    DPlayStreamingButton(
                        onClick = onStreamClick,
                        enabled = true,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DPlayLikeButton(
                        isLiked = state.like.isLiked,
                        likeCount = state.like.count,
                        onClick = onLikeClick,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier =
                        horizontalModifier
                            .border(
                                width = 1.dp,
                                color = color.gray200,
                                shape = RoundedCornerShape(12.dp),
                            ).roundedBackgroundWithPadding(
                                backgroundColor = color.dplayWhite,
                                cornerRadius = 12.dp,
                                padding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                            ),
                ) {
                    Text(
                        text = state.content,
                        style = typography.bodySemi14,
                        color = color.dplayBlack,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .noRippleClickable(onClick = onWriterProfileClick),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = state.writer.profileImg ?: R.drawable.base_profile_image,
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, color = color.gray200, shape = CircleShape),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = state.writer.nickname,
                            style = typography.bodySemi14,
                            color = color.gray400,
                        )
                    }
                }
            }
        }
        if (state.bottomSheetVisible) {
            val bottomSheetModifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .noRippleClickable()

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = DPlayTheme.colors.dim40)
                        .noRippleClickable { changeBottomSheetVisible(false) },
            )

            if (state.isHost) {
                DPlayButtonBottomSheet(
                    mainText = "삭제하기",
                    subText = "취소하기",
                    mainOnClick = onDeleteClick,
                    subOnClick = {
                        changeBottomSheetVisible(false)
                    },
                    mainButtonColor = DPlayTheme.colors.dplayPink,
                    modifier = bottomSheetModifier,
                )
            } else {
                DPlayReportBottomSheet(
                    onCloseClick = { changeBottomSheetVisible(false) },
                    onButtonClick = { changeBottomSheetVisible(false) },
                    modifier = bottomSheetModifier,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun DetailScreenPreview() {
    DPlayTheme {
        DetailScreen(
            onTopAppBarLeftIconClick = {},
            onTopAppBarRightIconClick = {},
            onBookmarkClick = {},
            onStreamClick = {},
            onLikeClick = {},
            onWriterProfileClick = {},
            changeBottomSheetVisible = {},
            onDeleteClick = {},
        )
    }
}
