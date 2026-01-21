package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.designsystem.util.roundedBackgroundWithPadding

@Composable
fun DPlayMusicListItem(
    musicImageUrl: String,
    musicName: String,
    musicArtistName: String,
    musicContent: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEditorPick: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, shape = RoundedCornerShape(20.dp), color = DPlayTheme.colors.gray200)
                    .roundedBackgroundWithPadding(
                        cornerRadius = 20.dp,
                        backgroundColor = DPlayTheme.colors.dplayWhite,
                        padding = PaddingValues(12.dp),
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = musicImageUrl,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(68.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                DplayClickableIcon(
                    iconRes = R.drawable.ic_more_gray_20,
                    onClick = onMoreClick,
                    modifier = Modifier.align(Alignment.End),
                )
                BoxWithConstraints {
                    val maxTitleWidth = maxWidth * 0.5f

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = musicName,
                            style = DPlayTheme.typography.bodyBold16,
                            color = DPlayTheme.colors.dplayBlack,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = maxTitleWidth)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = musicArtistName,
                            style = DPlayTheme.typography.capMed12,
                            color = DPlayTheme.colors.gray400,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = musicContent,
                    style = DPlayTheme.typography.capMed12,
                    color = DPlayTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (isEditorPick) {
            Box(
                modifier =
                    Modifier
                        .padding(start = 64.dp, top = 8.dp)
                        .clip(shape = CircleShape)
                        .border(
                            width = 1.dp,
                            color = DPlayTheme.colors.dplayPink,
                            shape = CircleShape,
                        ).background(color = DPlayTheme.colors.dplayWhite)
                        .padding(4.dp),
            ) {
                DplayBaseIcon(
                    iconRes = R.drawable.ic_editor_20,
                    modifier = Modifier.size(12.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DPlayMusicListItemPreview() {
    DPlayTheme {
        Column {
            DPlayMusicListItem(
                musicImageUrl = "",
                musicName = "Title",
                musicArtistName = "artist",
                musicContent = "contents",
                onMoreClick = {},
            )
            DPlayMusicListItem(
                musicImageUrl = "",
                musicName = "Title",
                musicArtistName = "artist",
                musicContent = "contents",
                onMoreClick = {},
                isEditorPick = true,
            )
        }
    }
}
