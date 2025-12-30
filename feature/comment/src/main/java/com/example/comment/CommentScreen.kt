package com.example.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.DPlayTheme
import com.example.ui.model.Music

@Composable
fun CommentRoute(
    musicInfo: Music,
    modifier: Modifier = Modifier
) {
    CommentScreen(
        modifier = modifier,
        musicInfo = musicInfo,
    )
}

@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    musicInfo: Music? = null,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "musicInfo: $musicInfo")
    }
}

@Preview
@Composable
fun CommentPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        CommentScreen()
    }
}