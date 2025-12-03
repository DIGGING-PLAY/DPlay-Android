package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayCheckBox(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, checkBoxIconRes) =
        if (isChecked) {
            DPlayTheme.colors.dplayPink100 to R.drawable.ic_check_circle_pink_24
        } else {
            DPlayTheme.colors.gray100 to R.drawable.ic_check_circle_lightgray_24
        }
    Row(
        modifier =
            modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp),
                ).noRippleClickable(
                    onClick = onClick,
                ).padding(
                    horizontal = 12.dp,
                    vertical = 16.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DplayBaseIcon(
            iconRes = checkBoxIconRes,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = DPlayTheme.typography.bodySemi16,
            color = DPlayTheme.colors.dplayBlack,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DPlayCheckBoxPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayCheckBox(
                text = stringResource(R.string.agree_all_terms),
                isChecked = true,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheckBox(
                text = stringResource(R.string.agree_all_terms),
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
