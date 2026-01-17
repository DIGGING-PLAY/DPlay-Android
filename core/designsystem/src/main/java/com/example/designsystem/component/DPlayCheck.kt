package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayCheck(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isChecked) DPlayTheme.colors.gray100 else Color.Transparent

    Row(
        modifier =
            modifier
                .background(color = backgroundColor)
                .noRippleClickable(
                    onClick = onClick,
                ).padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isChecked) {
            DplayBaseIcon(
                iconRes = R.drawable.ic_check_circle_20,
                modifier = Modifier.size(19.6.dp),
            )

            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            text = text,
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.gray500,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DPlayCheckPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayCheck(
                text = stringResource(id = R.string.reason_inappropriate_content),
                isChecked = true,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheck(
                text = stringResource(id = R.string.reason_inappropriate_content),
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheck(
                text = stringResource(id = R.string.reason_offensive_expression),
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheck(
                text = stringResource(id = R.string.reason_suspicious_or_spam),
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheck(
                text = stringResource(id = R.string.reason_copyright_violation),
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
