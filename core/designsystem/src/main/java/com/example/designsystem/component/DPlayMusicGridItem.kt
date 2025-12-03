package com.example.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.theme.defaultDPlayColors
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayMusicGridItem(
    musicImageUrl: String,
    musicName: String,
    musicArtistName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier.noRippleClickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = DPlayTheme.colors.gray400,
                        shape = CircleShape,
                    ).drawWithContent {
                        drawContent()

                        val cx = size.width / 2
                        val cy = size.height / 2
                        val holeRadius = 10.dp.toPx()

                        drawCircle(
                            color = Color.Transparent,
                            radius = holeRadius,
                            center = Offset(cx, cy),
                            blendMode = BlendMode.Clear,
                        )

                        drawCircle(
                            color = defaultDPlayColors.gray400,
                            radius = holeRadius,
                            center = Offset(cx, cy),
                            style = Stroke(width = 2.dp.toPx()),
                        )
                    },
        ) {
            AsyncImage(
                model = musicImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = musicName, style = DPlayTheme.typography.bodySemi14, color = DPlayTheme.colors.dplayBlack)
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = musicArtistName, style = DPlayTheme.typography.capMed12, color = DPlayTheme.colors.gray400)
    }
}

@Preview
@Composable
private fun DPlayMusicGridItemPreview() {
    DPlayTheme {
        Row(modifier = Modifier.fillMaxWidth()) {
            DPlayMusicGridItem(
                musicImageUrl = "",
                musicName = "Title",
                musicArtistName = "artist",
                modifier = Modifier.weight(1f),
            )
            DPlayMusicGridItem(
                musicImageUrl = "",
                musicName = "Title",
                musicArtistName = "artist",
                modifier = Modifier.weight(1f),
            )
            DPlayMusicGridItem(
                musicImageUrl = "",
                musicName = "Title",
                musicArtistName = "artist",
                modifier = Modifier.weight(1f),
            )
        }
    }
}
