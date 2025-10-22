package com.example.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object DPlayTheme {
    val typography: DPlayTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDPlayTypography.current
}

@Composable
fun DPlayTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDPlayTypography provides DefaultDPlayTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}