package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
fun DPlaySmallGrayButton(
    modifier : Modifier = Modifier,
    label: String = "Button",
    onClick: () -> Unit = {}
){
    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
        containerColor = DPlayTheme.colors.gray600,
        borderColor = DPlayTheme.colors.gray600,
    ) {
        Text(
            text = label,
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.dplayWhite
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DPlaySmallGrayButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DPlaySmallGrayButton(
                label = stringResource(R.string.recommend_music_button_label)
            )
        }
    }
}