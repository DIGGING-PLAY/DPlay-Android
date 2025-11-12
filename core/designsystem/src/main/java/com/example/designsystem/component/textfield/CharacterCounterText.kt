package com.example.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun CharacterCounterText(
    currentLength: Int,
    maxLength: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.text_field_character_counter, currentLength, maxLength),
        modifier = modifier,
        color = DPlayTheme.colors.gray400,
        style = DPlayTheme.typography.capMed12,
    )
}