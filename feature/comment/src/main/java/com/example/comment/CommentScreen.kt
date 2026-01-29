package com.example.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayMusicGridItem
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.DplayTooltip
import com.example.designsystem.component.button.DPlayGuidelineButton
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.textfield.DPlayTextArea
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Home
import com.example.navigation.Navigator
import com.example.ui.model.TrackState

@Composable
fun CommentRoute(
    track: TrackState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: CommentViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(track) {
        viewModel.handleIntent(CommentContract.CommentIntent.Initialize(track))
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                CommentContract.CommentSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                CommentContract.CommentSideEffect.NavigateToHome -> {
                    navigator.clearAndNavigateTo(Home)
                }
                is CommentContract.CommentSideEffect.OpenWebView -> {
                    uriHandler.openUri(sideEffect.url)
                }
            }
        }
    }

    CommentScreen(
        state = state,
        modifier = modifier,
        onBackIconClick = { viewModel.handleIntent(CommentContract.CommentIntent.OnBackIconClick) },
        onGuideButtonClick = { viewModel.handleIntent(CommentContract.CommentIntent.OnGuideButtonClick) },
        onMoreGuideClick = { viewModel.handleIntent(CommentContract.CommentIntent.OnMoreGuideClick) },
        onGuideXIconClick = { viewModel.handleIntent(CommentContract.CommentIntent.OnGuideXIconClick) },
        onCommentInputChanged = { viewModel.handleIntent(CommentContract.CommentIntent.OnCommentInputChanged(it)) },
        onRegisterButtonClick = { viewModel.handleIntent(CommentContract.CommentIntent.OnRegisterButtonClick) },
    )
}

@Composable
fun CommentScreen(
    state: CommentContract.CommentState,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onGuideButtonClick: () -> Unit = {},
    onMoreGuideClick: () -> Unit = {},
    onGuideXIconClick: () -> Unit = {},
    onCommentInputChanged: (String) -> Unit = {},
    onRegisterButtonClick: () -> Unit = {},
) {
    var guideButtonHeightPx by remember { mutableIntStateOf(0) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
                .padding(bottom = 16.dp),
    ) {
        DplayLeftIconTitleTopAppBar(
            title = stringResource(com.dplay.comment.R.string.comment_top_bar_title),
        ) {
            onBackIconClick()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(com.dplay.comment.R.string.comment_title),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            modifier = Modifier.padding(start = 16.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        DPlayMusicGridItem(
            musicImageUrl = state.track?.thumbnailUrl ?: "",
            musicName = state.track?.musicTitle ?: "",
            musicArtistName = state.track?.artistName ?: "",
            modifier =
                Modifier
                    .width(132.dp)
                    .align(Alignment.CenterHorizontally),
            titleStyle = DPlayTheme.typography.titleBold18,
            artistStyle = DPlayTheme.typography.bodySemi14,
            spaceBetweenCover = 12.dp,
            spaceBetweenText = 6.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        DPlayTextArea(
            value = state.commentInput,
            onValueChange = { onCommentInputChanged(it) },
            placeholder = stringResource(id = R.string.placeholder_comment),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier =
                Modifier
                    .padding(start = 16.dp)
                    .zIndex(1f),
        ) {
            DPlayGuidelineButton(
                onClick = { onGuideButtonClick() },
                modifier =
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            guideButtonHeightPx = coordinates.size.height
                        },
            )

            if (state.isGuideVisible) {
                DplayTooltip(
                    onTextButtonClicked = { onMoreGuideClick() },
                    onCloseButtonClicked = { onGuideXIconClick() },
                    modifier =
                        Modifier
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(0, 0) {
                                    val topMargin = 8.dp.roundToPx()
                                    placeable.place(x = 0, y = guideButtonHeightPx + topMargin)
                                }
                            },
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        DPlayLargePinkButton(
            onClick = { onRegisterButtonClick() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            label = stringResource(R.string.register_button_label),
            enabled = state.isRegisterButtonEnabled,
        )
    }
}

@Preview
@Composable
fun CommentPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        CommentScreen(
            state =
                CommentContract.CommentState(
                    isGuideVisible = true,
                ),
        )
    }
}
