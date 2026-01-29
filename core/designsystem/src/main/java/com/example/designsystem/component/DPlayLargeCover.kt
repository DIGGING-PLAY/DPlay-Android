package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayLargeCover(
    isLikeChecked: Boolean,
    likeCount: Int,
    writerProfileImageUrl: String?,
    writerNickname: String,
    content: String,
    musicImageUrl: String,
    onCoverClick: () -> Unit,
    onWriterProfileClick: () -> Unit,
    onStreamClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLocked: Boolean = true,
    isStreaming: Boolean = false,
) {
    val color = DPlayTheme.colors
    val typography = DPlayTheme.typography

    val likeCountString = likeCount.takeIf { it > 0 }?.toString().orEmpty()
    val textCoverShape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)

    var discHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    val textHeightDp =
        remember(discHeightPx) {
            if (discHeightPx == 0) {
                0.dp
            } else {
                with(density) { discHeightPx.toDp() * (204f / 255f) }
            }
        }

    Box(modifier = modifier.fillMaxWidth().clip(textCoverShape)) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .then(if (isLocked) Modifier.blur(20.dp) else Modifier)
                    .noRippleClickable(onClick = onCoverClick),
        ) {
            DPlayMusicDiscItem(
                imageUrl = musicImageUrl,
                isStreaming = isStreaming,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 44.dp, start = 12.dp, end = 12.dp)
                        .onSizeChanged { discHeightPx = it.height }
                        .align(Alignment.TopCenter),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(textHeightDp)
                        .background(
                            color = color.dplayPinkTrans,
                            shape = textCoverShape,
                        ).border(
                            width = 1.dp,
                            color = color.dplayPink,
                            shape = textCoverShape,
                        ).padding(12.dp)
                        .align(Alignment.BottomCenter),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.noRippleClickable(onClick = onWriterProfileClick),
                ) {
                    AsyncImage(
                        model = writerProfileImageUrl ?: R.drawable.base_profile_image,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(1.dp, color = color.gray200, shape = CircleShape),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = writerNickname,
                        style = typography.bodyBold16,
                        color = color.dplayWhite,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    DplayBaseIcon(
                        iconRes = R.drawable.ic_quote_up_16,
                        modifier = Modifier.align(Alignment.Top),
                    )
                    Text(
                        text = content,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.bodySemi14,
                        color = color.dplayWhite,
                        modifier = Modifier.weight(1f),
                    )
                    DplayBaseIcon(
                        iconRes = R.drawable.ic_quote_down_16,
                        modifier = Modifier.align(Alignment.Bottom),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.Bottom) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        DplayClickableIcon(
                            iconRes =
                                if (isLikeChecked) {
                                    R.drawable.ic_heart_white_filled_24
                                } else {
                                    R.drawable.ic_heart_white_unfilled_24
                                },
                            onClick = onLikeClick,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = likeCountString,
                            style = typography.bodySemi14,
                            color = color.dplayWhite,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    DplayClickableIcon(
                        iconRes = R.drawable.ic_stream_pink_32,
                        modifier =
                            Modifier
                                .background(
                                    color = color.dplayWhite,
                                    shape = RoundedCornerShape(16.dp),
                                ).padding(10.dp),
                        onClick = onStreamClick,
                    )
                }
            }
        }

        if (isLocked) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .noRippleClickable { onCoverClick() }
                        .background(color.dplayWhite.copy(alpha = 0.9f))
                        .blur(20.dp),
            )
            Column(modifier = Modifier.matchParentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(4f))
                DplayBaseIcon(iconRes = R.drawable.ic_lock_140)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "앗, 아직 열어 볼 수 없어요!", style = typography.bodyBold16, color = color.dplayBlack)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "잠겨있는 곡이에요", style = typography.bodyMed14, color = color.gray400)
                Spacer(modifier = Modifier.weight(6f))
            }
        }
    }
}

@Preview
@Composable
private fun DPlayLargeCoverPreview() {
    DPlayTheme {
        DPlayLargeCover(
            isLikeChecked = false,
            likeCount = 24,
            writerProfileImageUrl = "",
            writerNickname = "윤서얌",
            content = "진짜 나오자마자 들었는데 이 노래가 최고! 출근곡, 퇴근곡, 노동곡 다 되는 짱제로! 일하는 매장에서도 수십 번씩 틀고 있어요. 모두가 알아야 돼..",
            musicImageUrl = "",
            onStreamClick = {},
            onLikeClick = {},
            onCoverClick = {},
            onWriterProfileClick = {},
        )
    }
}
