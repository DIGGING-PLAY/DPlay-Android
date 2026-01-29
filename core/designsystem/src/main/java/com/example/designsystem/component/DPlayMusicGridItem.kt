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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
    titleStyle: TextStyle = DPlayTheme.typography.bodySemi14,
    artistStyle: TextStyle = DPlayTheme.typography.capMed12,
    spaceBetweenCover: Dp = 5.dp,
    spaceBetweenText : Dp = 1.dp
) {
    Column(modifier = modifier.noRippleClickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        DPlayMusicDiscItem(
            imageUrl = musicImageUrl,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(spaceBetweenCover))
        Text(
            text = musicName,
            style = titleStyle,
            color = DPlayTheme.colors.dplayBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(spaceBetweenText))
        Text(
            text = musicArtistName,
            style = artistStyle,
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
