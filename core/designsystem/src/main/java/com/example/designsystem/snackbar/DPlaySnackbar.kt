package com.example.designsystem.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlaySnackbar(
    @DrawableRes iconRes: Int,
    description: String,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(8.dp),
                ).background(
                    color = DPlayTheme.colors.gray500,
                ).padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = description,
            color = DPlayTheme.colors.dplayWhite,
            style = DPlayTheme.typography.bodyMed14,
        )

        Spacer(modifier = Modifier.weight(1f))

        content?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun DPlaySnackbarPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            DPlaySnackbar(
                iconRes = R.drawable.ic_check_circle_pink_24,
                description = stringResource(R.string.add_success_snackbar_description),
            ) {
                Text(
                    text = stringResource(R.string.add_success_snackbar_action_label),
                    color = DPlayTheme.colors.dplayPink,
                    style = DPlayTheme.typography.bodyBold14,
                )
            }
        }
    }
}
