package com.example.ui.controller

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

@Stable
class ModalController {
    private val _modalState = mutableStateOf<ModalState?>(null)
    val modalState: State<ModalState?> = _modalState

    fun showGraphicModal(
        mainText: String,
        subText: String?,
        buttonLabel: String,
        onButtonClick: () -> Unit = {},
        onDismiss: () -> Unit = {},
    ) {
        _modalState.value =
            ModalState.GraphicModal(
                mainText = mainText,
                subText = subText,
                onButtonClick = onButtonClick,
                onDismiss = onDismiss,
                buttonLabel = buttonLabel,
            )
    }

    fun showWarningModal(
        mainText: String,
        subText: String?,
        onLeftButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit,
        onDismiss: () -> Unit,
        leftButtonLabel: String,
        rightButtonLabel: String,
    ) {
        _modalState.value =
            ModalState.WarningModal(
                mainText = mainText,
                subText = subText,
                onLeftButtonClick = onLeftButtonClick,
                onRightButtonClick = onRightButtonClick,
                onDismiss = onDismiss,
                leftButtonLabel = leftButtonLabel,
                rightButtonLabel = rightButtonLabel,
            )
    }

    fun hideModal() {
        _modalState.value = null
    }
}

sealed class ModalState {
    data class GraphicModal(
        val mainText: String,
        val subText: String?,
        val onButtonClick: () -> Unit,
        val onDismiss: () -> Unit,
        val buttonLabel: String,
    ) : ModalState()

    data class WarningModal(
        val mainText: String,
        val subText: String?,
        val onLeftButtonClick: () -> Unit,
        val onRightButtonClick: () -> Unit,
        val onDismiss: () -> Unit,
        val leftButtonLabel: String,
        val rightButtonLabel: String,
    ) : ModalState()
}

val LocalModalController =
    compositionLocalOf<ModalController> {
        error("DialogController not provided")
    }
