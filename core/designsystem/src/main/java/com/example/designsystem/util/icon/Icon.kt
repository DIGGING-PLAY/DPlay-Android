package com.example.designsystem.util.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.designsystem.util.noRippleClickable

@Composable
fun DplayBaseIcon(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
) {
    Icon(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(iconRes),
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun DplayClickableIcon(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enablePressedAnimation: Boolean = false,
) {
    val clickableModifier =
        if (enablePressedAnimation) {
            modifier.clickable(onClick = onClick)
        } else {
            modifier.noRippleClickable(onClick = onClick)
        }
    DplayBaseIcon(
        iconRes = iconRes,
        modifier = clickableModifier,
    )
}
