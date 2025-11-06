package com.example.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayLargeGrayButton(
    modifier : Modifier = Modifier,
    label: String = "Button",
    enabled: Boolean = true,
    onClick: () -> Unit = {}
){
    val containerColor = if(enabled) DPlayTheme.colors.gray600 else DPlayTheme.colors.gray200
    val textColor = if(enabled) DPlayTheme.colors.dplayWhite else DPlayTheme.colors.gray400

    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        paddingValues = PaddingValues(vertical = 16.dp),
        containerColor = containerColor,
        borderColor = containerColor,
    ) {
        Text(
            text = label,
            style = DPlayTheme.typography.bodyBold16,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayLargeGrayButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DPlayLargeGrayButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.report_button_label)
            )

            DPlayLargeGrayButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.report_button_label),
                enabled = false
            )

            DPlayLargeGrayButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.apply_button_label)
            )
        }
    }
}