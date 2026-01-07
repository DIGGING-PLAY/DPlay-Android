package com.example.designsystem.component.snackbar

import androidx.compose.runtime.compositionLocalOf
import com.example.designsystem.component.snackbar.type.SnackBarType

val LocalSnackBarState = compositionLocalOf<SnackBarType?> { null }
val LocalShowSnackBar = compositionLocalOf<(SnackBarType, (() -> Unit)?) -> Unit> { { _, _ -> } }
