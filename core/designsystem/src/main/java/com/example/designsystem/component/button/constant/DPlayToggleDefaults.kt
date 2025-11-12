package com.example.designsystem.component.button.constant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DPlayTheme

@Immutable
data class SwitchSizes(
    val padding: Dp = 2.dp,
    val cornerRadius: Dp = 16.dp,
    val thumbSize: Dp = 24.dp
) {
    val containerWidth: Dp get() = (thumbSize + padding) * 2
    val containerHeight: Dp get() = thumbSize + (padding * 2)
}

@Immutable
data class SwitchColors(
    val checkedContainer: Color,
    val uncheckedContainer: Color,
    val thumb: Color
)

object DPlayToggleDefaults {
    @Composable
    fun colors(
        checkedContainer: Color = DPlayTheme.colors.dplayPink,
        uncheckedContainer: Color = DPlayTheme.colors.gray300,
        thumb: Color = DPlayTheme.colors.dplayWhite
    ): SwitchColors = SwitchColors(
        checkedContainer = checkedContainer,
        uncheckedContainer = uncheckedContainer,
        thumb = thumb
    )

    fun sizes(
        padding: Dp = 2.dp,
        cornerRadius: Dp = 16.dp,
        thumbSize: Dp = 24.dp
    ): SwitchSizes = SwitchSizes(
        padding = padding,
        cornerRadius = cornerRadius,
        thumbSize = thumbSize
    )
}