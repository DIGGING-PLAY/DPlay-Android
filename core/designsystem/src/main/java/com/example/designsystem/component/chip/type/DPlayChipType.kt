package com.example.designsystem.component.chip.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dplay.designsystem.R

enum class DPlayChipType(
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int,
) {
    EDITOR(
        drawableRes = R.drawable.editor_chip,
        stringRes = R.string.chip_editor,
    ),
    NEW(
        drawableRes = R.drawable.new_chip,
        stringRes = R.string.chip_new,
    ),
    BEST(
        drawableRes = R.drawable.best_chip,
        stringRes = R.string.chip_best,
    ),
}
