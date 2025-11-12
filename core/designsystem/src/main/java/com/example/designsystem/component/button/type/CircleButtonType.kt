package com.example.designsystem.component.button.type

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.defaultDPlayColors

sealed class CircleButtonType {
    abstract val containerSize: Dp
    abstract val backgroundColor: Color
    abstract val iconSize: Dp
    abstract val iconTint: Color
    abstract val iconRes: Int
    abstract val contentDescription: Int

    data class SmallClose(
        override var contentDescription: Int = R.string.circle_close_button_icon_default_description
    ) : CircleButtonType() {
        override val containerSize = 20.dp
        override val backgroundColor = defaultDPlayColors.gray200
        override val iconRes = R.drawable.ic_close_20
        override val iconSize = 20.dp
        override val iconTint = defaultDPlayColors.gray400
    }

    data class SmallEdit(
        override val contentDescription: Int = R.string.circle_edit_button_icon_default_description
    ) : CircleButtonType() {
        override val containerSize = 24.dp
        override val backgroundColor = defaultDPlayColors.gray200
        override val iconRes = R.drawable.ic_pen_24
        override val iconSize = 24.dp
        override val iconTint = defaultDPlayColors.gray400
    }

    data class SmallPlus(
        override val contentDescription: Int = R.string.circle_plus_button_icon_default_description
    ) : CircleButtonType() {
        override val containerSize = 28.dp
        override val backgroundColor = defaultDPlayColors.dplayBlack
        override val iconRes = R.drawable.ic_plus_28
        override val iconSize = 28.dp
        override val iconTint = defaultDPlayColors.dplayWhite
    }

    data class LargePlus(
        override val contentDescription: Int = R.string.circle_plus_button_icon_default_description
    ) : CircleButtonType() {
        override val containerSize = 56.dp
        override val backgroundColor = defaultDPlayColors.dplayBlack
        override val iconRes = R.drawable.ic_plus_28
        override val iconSize = 28.dp
        override val iconTint = defaultDPlayColors.dplayWhite
    }
}