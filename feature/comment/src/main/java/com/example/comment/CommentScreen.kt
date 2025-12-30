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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayMusicGridItem
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.DplayTooltip
import com.example.designsystem.component.button.DPlayGuidelineButton
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.textfield.DPlayTextArea
import com.example.designsystem.theme.DPlayTheme
import com.example.ui.model.Music

@Composable
fun CommentRoute(
    musicInfo: Music,
    modifier: Modifier = Modifier
) {
    CommentScreen(
        state = CommentContract.CommentState(),
        modifier = modifier,
        musicInfo = musicInfo,
    )
}

@Composable
fun CommentScreen(
    state: CommentContract.CommentState,
    modifier: Modifier = Modifier,
    musicInfo: Music? = null,
) {
    var guideButtonHeightPx by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(bottom = 16.dp),
    ) {
        DplayLeftIconTitleTopAppBar(
            title = "노래 등록하기"
        ) {

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "노래에 대한\n이야기를 작성해보세요!",
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        DPlayMusicGridItem(
            musicImageUrl = musicInfo?.thumbnailUrl ?: "",
            musicName = musicInfo?.musicTitle ?: "",
            musicArtistName = musicInfo?.artistName ?: "",
            modifier = Modifier
                .width(132.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DPlayTextArea(
            value = state.commentInput,
            onValueChange = { state.commentInput },
            placeholder = stringResource(id = R.string.placeholder_comment),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .zIndex(1f)
        ) {
            DPlayGuidelineButton(
                onClick = {  },
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        guideButtonHeightPx = coordinates.size.height
                    }
            )

            if (state.isGuideVisible) {
                DplayTooltip(
                    onTextButtonClicked = {},
                    onCloseButtonClicked = {},
                    modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(0, 0) {
                                val topMargin = 8.dp.roundToPx()
                                placeable.place(x = 0, y = guideButtonHeightPx + topMargin)
                            }
                        }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        DPlayLargePinkButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            label = stringResource(R.string.register_button_label),
            enabled = state.isRegisterButtonEnabled
        )
    }
}

@Preview
@Composable
fun CommentPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        CommentScreen(
            state = CommentContract.CommentState(
                isGuideVisible = true
            ),
        )
    }
}