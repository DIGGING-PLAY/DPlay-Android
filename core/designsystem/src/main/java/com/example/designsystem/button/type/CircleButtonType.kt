package com.example.designsystem.button.type

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
    abstract val contentDescription: String

    data class SmallClose(
        override val contentDescription: String = "닫기"
    ) : CircleButtonType() {
        override val containerSize = 20.dp
        override val backgroundColor = defaultDPlayColors.gray200
        override val iconRes = R.drawable.ic_close_20
        override val iconSize = 20.dp
        override val iconTint = defaultDPlayColors.gray400
    }

    data class SmallEdit(
        override val contentDescription: String = "수정"
    ) : CircleButtonType() {
        override val containerSize = 24.dp
        override val backgroundColor = defaultDPlayColors.gray200
        override val iconRes = R.drawable.ic_pen_24
        override val iconSize = 24.dp
        override val iconTint = defaultDPlayColors.gray400
    }

    data class SmallPlus(
        override val contentDescription: String = "추가"
    ) : CircleButtonType() {
        override val containerSize = 28.dp
        override val backgroundColor = defaultDPlayColors.dplayBlack
        override val iconRes = R.drawable.ic_plus_28
        override val iconSize = 28.dp
        override val iconTint = defaultDPlayColors.dplayWhite
    }

    data class LargePlus(
        override val contentDescription: String = "추가"
    ) : CircleButtonType() {
        override val containerSize = 56.dp
        override val backgroundColor = defaultDPlayColors.dplayBlack
        override val iconRes = R.drawable.ic_plus_28
        override val iconSize = 28.dp
        override val iconTint = defaultDPlayColors.dplayWhite
    }
}