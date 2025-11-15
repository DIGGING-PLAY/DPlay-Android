package com.example.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.theme.defaultDPlayColors
import com.example.designsystem.util.roundedBackgroundWithPadding

@Composable
fun DplayTooltip(
    onCloseButtonClicked: () -> Unit,
    onTextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(start = 54.dp)
                .size(width = 16.dp, height = 10.dp)
                .drawBehind {
                    val width = 16.dp.toPx()
                    val height = 10.dp.toPx()
                    val center = size.width / 2f

                    val path = Path().apply {
                        moveTo(center - width / 2f, height)
                        lineTo(center + width / 2f, height)
                        lineTo(center, 0f)
                        close()
                    }
                    drawPath(path, color = defaultDPlayColors.gray600)
                }
        )


        Column(
            modifier = Modifier
                .roundedBackgroundWithPadding(
                    backgroundColor = DPlayTheme.colors.gray600,
                    cornerRadius = 4.dp,
                    padding = PaddingValues(vertical = 16.dp, horizontal = 12.dp)
                )

        ) {
            Row {
                Text(
                    style = DPlayTheme.typography.bodyMed14,
                    color = DPlayTheme.colors.dplayWhite,
                    text = stringResource(R.string.tooltip_default_description),
                )
                DplayClickableIcon(
                    iconRes = R.drawable.ic_close_24,
                    onClick = onCloseButtonClicked,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // TODO: DplayUnderlineTextButton(onClick = onTextButtonClicked) 머지되면 변경
            Text(
                style = DPlayTheme.typography.bodyMed14,
                color = DPlayTheme.colors.dplayWhite,
                text = "더 알아보기"
            )
        }
    }
}

@Preview
@Composable
private fun DplayTooltipPreview() {
    DPlayTheme {
        DplayTooltip(
            onCloseButtonClicked = {},
            onTextButtonClicked = {}
        )
    }
}