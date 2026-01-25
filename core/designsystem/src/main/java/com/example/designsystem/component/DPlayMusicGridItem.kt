package com.example.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DPlayTheme
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
        DPlayMusicDiscItem(
            imageUrl = musicImageUrl,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = musicName,
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.dplayBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = musicArtistName,
            style = DPlayTheme.typography.capMed12,
            color = DPlayTheme.colors.gray400,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

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
