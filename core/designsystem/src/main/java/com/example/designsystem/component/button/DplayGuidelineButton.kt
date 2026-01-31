package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun DPlayGuidelineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 12.dp),
        containerColor = DPlayTheme.colors.dplayWhite,
        borderColor = DPlayTheme.colors.gray200,
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DplayBaseIcon(
                iconRes = R.drawable.ic_info_20,
                contentDescription = stringResource(R.string.guideline_button_icon_description),
            )

            Spacer(
                modifier = Modifier.size(4.dp),
            )

            Text(
                text = stringResource(R.string.guideline_button_label),
                style = DPlayTheme.typography.capMed12,
                color = DPlayTheme.colors.gray400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayGuidelineButtonPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayGuidelineButton(
                onClick = {},
            )
        }
    }
}
