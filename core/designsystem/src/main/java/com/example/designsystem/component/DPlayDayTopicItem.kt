package com.example.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.roundedBackgroundWithPadding

@Composable
fun DPlayDayTopicItem(
    day: Int,
    topic: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "${day}일",
            modifier =
                Modifier
                    .roundedBackgroundWithPadding(
                        backgroundColor = DPlayTheme.colors.gray600,
                        cornerRadius = 8.dp,
                        padding = PaddingValues(horizontal = 16.5.dp, vertical = 16.dp),
                    ),
            color = DPlayTheme.colors.gray100,
            style = DPlayTheme.typography.bodyBold14,
        )
        Text(
            text = topic,
            modifier =
                Modifier
                    .weight(1f)
                    .roundedBackgroundWithPadding(
                        backgroundColor = DPlayTheme.colors.gray100,
                        cornerRadius = 8.dp,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 16.dp),
                    ),
            style = DPlayTheme.typography.bodySemi14,
        )
    }
}

@Preview
@Composable
private fun DPlayDayTopicItemPreview() {
    DPlayTheme {
        DPlayDayTopicItem(
            day = 1,
            topic = "여행 갈 때 플레이리스트에 꼭 넣는 노래는?",
        )
    }
}
