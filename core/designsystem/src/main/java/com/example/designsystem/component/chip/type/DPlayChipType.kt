package com.example.designsystem.component.chip.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dplay.designsystem.R

enum class DPlayChipType(
    @DrawableRes val iconRes: Int,
    @StringRes val stringRes: Int,
) {
    EDITOR(
        iconRes = R.drawable.ic_editor_20,
        stringRes = R.string.chip_editor,
    ),
    NEW(
        iconRes = R.drawable.ic_new_20,
        stringRes = R.string.chip_new,
    ),
    BEST(
        iconRes = R.drawable.ic_best_20,
        stringRes = R.string.chip_best,
    ),
}
