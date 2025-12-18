package com.example.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayMusicDiscItem
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showSnackBar = LocalShowSnackBar.current


    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is DetailContract.DetailSideEffect.ShowToast -> {
                    showSnackBar(it.snackBarType, it.action)
                }

            }
        }
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is DetailContract.DetailSideEffect.ShowSnackBar -> {
                }
            }
        }
    }

    DetailScreen(
        state = uiState,
        onTopAppBarLeftIconClick = TODO(),
        onTopAppBarRightIconClick = TODO(),
        onBookmarkClick = TODO(),
        onStreamClick = TODO(),
        onLickClick = TODO(),
        onWriterProfileClick = TODO(),
    )
}

@Composable
private fun DetailScreen(
    onTopAppBarLeftIconClick: () -> Unit,
    onTopAppBarRightIconClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onStreamClick: () -> Unit,
    onLickClick: () -> Unit,
    onWriterProfileClick: () -> Unit,
    state: DetailContract.DetailState = DetailContract.DetailState(),
) {
    val color = DPlayTheme.colors
    val typography = DPlayTheme.typography
    val postDetailData = state.data
    val horizontalModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    val chipType: DPlayChipType? =
        when {
            postDetailData.badges.isPopular -> DPlayChipType.BEST
            postDetailData.badges.isEditorPick -> DPlayChipType.EDITOR
            postDetailData.badges.isNew -> DPlayChipType.NEW
            else -> null
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
                imageUrl = postDetailData.track.coverImg,
                isStreaming = false,
            )
            DPlayBookmarkButton(
                isMarked = postDetailData.isScrapped,
                onClick = onBookmarkClick,
                modifier = Modifier.align(Alignment.TopEnd),
            )
            chipType
                ?.let {
                    DPlayChip(
                        type = it,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = postDetailData.track.songTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = typography.bodyBold20,
            color = color.dplayBlack,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = postDetailData.track.artistName,
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
                isLiked = postDetailData.like.isLiked,
                likeCount = postDetailData.like.count,
                onClick = onLickClick,
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
                    )
                    .roundedBackgroundWithPadding(
                        backgroundColor = color.dplayWhite,
                        cornerRadius = 12.dp,
                        padding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                    ),
        ) {
            Text(
                text = postDetailData.content,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodySemi14,
                color = color.dplayBlack
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .noRippleClickable(onClick = onWriterProfileClick)
            ) {
                AsyncImage(
                    model = postDetailData.writer.profileImg,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(1.dp, color = color.gray200, shape = CircleShape),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = postDetailData.writer.nickname,
                    style = typography.bodySemi14,
                    color = color.gray400
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
        DetailScreen()
    }
}
