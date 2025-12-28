package com.example.designsystem.component.snackbar.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dplay.designsystem.R

enum class SnackBarType(
    @param:DrawableRes val iconRes: Int,
    @param:StringRes val titleRes: Int,
    @param:StringRes val actionStringRes: Int? = null,
) {
    ADD(
        iconRes = R.drawable.ic_check_circle_pink_24,
        titleRes = R.string.snackbar_message_add_collection,
        actionStringRes = R.string.snackbar_action_message_navigate_collection,
    ),
    STREAMING_NOT_SUPPORT(
        iconRes = R.drawable.ic_warning_24,
        titleRes = R.string.snackbar_message_streaming_not_support,
        actionStringRes = null,
    ),
}
