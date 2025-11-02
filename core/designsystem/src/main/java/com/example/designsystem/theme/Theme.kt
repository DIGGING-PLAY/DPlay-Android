package com.example.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object DPlayTheme {
    val colors: DPlayColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDPlayColors.current

    val typography: DPlayTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDPlayTypography.current
}

@Composable
fun ProvideDPlayColorsAndTypography(
    colors: DPlayColors,
    typography: DPlayTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDPlayColors provides colors,
        LocalDPlayTypography provides typography,
        content = content
    )
}

@Composable
fun DPlayTheme(
    content: @Composable () -> Unit
) {
    ProvideDPlayColorsAndTypography(
        colors = defaultDPlayColors,
        typography = defaultDPlayTypography
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
                }
            }
        }

        MaterialTheme(
            content = content
        )
    }
}