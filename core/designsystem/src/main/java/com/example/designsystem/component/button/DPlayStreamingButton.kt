package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayStreamingButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (enabled) DPlayTheme.colors.dplayPink else DPlayTheme.colors.dplayPink300
    DPlayButtonSlot(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        paddingValues = PaddingValues(vertical = 8.dp),
        containerColor = containerColor,
        borderColor = containerColor,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DplayBaseIcon(
                    iconRes = R.drawable.ic_stream_white_32,
                    contentDescription = stringResource(R.string.streaming_button_icon_description),
                )

                Spacer(
                    modifier = Modifier.size(8.dp),
                )

                Text(
                    text = stringResource(R.string.streaming_button_label),
                    style = DPlayTheme.typography.bodySemi14,
                    color = DPlayTheme.colors.dplayWhite,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DPlayStreamingButtonPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DPlayStreamingButton(
                    onClick = {},
                    enabled = true,
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.size(8.dp))

                DPlayStreamingButton(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
