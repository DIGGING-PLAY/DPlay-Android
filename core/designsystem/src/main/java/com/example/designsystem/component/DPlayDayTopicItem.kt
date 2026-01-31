package com.example.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.designsystem.util.roundedBackgroundWithPadding

@Composable
fun DPlayDayTopicItem(
    dayText: String,
    topic: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick),
    ) {
        Box(
            modifier =
                Modifier
                    .size(50.dp)
                    .roundedBackgroundWithPadding(
                        backgroundColor = DPlayTheme.colors.gray600,
                        cornerRadius = 8.dp,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dayText,
                color = DPlayTheme.colors.gray100,
                style = DPlayTheme.typography.bodyBold14,
            )
        }
        Box(
            modifier =
                Modifier
                    .height(50.dp)
                    .weight(1f)
                    .roundedBackgroundWithPadding(
                        backgroundColor = DPlayTheme.colors.gray100,
                        cornerRadius = 8.dp,
                        padding = PaddingValues(start = 10.dp),
                    ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = topic,
                style = DPlayTheme.typography.bodySemi14,
            )
        }
    }
}

@Preview
@Composable
private fun DPlayDayTopicItemPreview() {
    DPlayTheme {
        DPlayDayTopicItem(
            dayText = "1일",
            topic = "여행 갈 때 플레이리스트에 꼭 넣는 노래는?",
            onClick = {},
        )
    }
}
