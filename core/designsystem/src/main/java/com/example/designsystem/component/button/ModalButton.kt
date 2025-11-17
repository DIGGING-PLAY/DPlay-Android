package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.util.noRippleClickable

@Composable
fun ModalButton(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .background(
                    color = backgroundColor,
                ).noRippleClickable(onClick = onClick)
                .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
