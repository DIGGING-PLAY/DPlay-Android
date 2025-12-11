package com.example.ui.handler

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.DPlayScrim
import com.example.designsystem.component.modal.GraphicModal
import com.example.designsystem.component.modal.WarningModal
import com.example.designsystem.theme.DPlayTheme
import com.example.ui.controller.LocalModalController
import com.example.ui.controller.ModalController
import com.example.ui.controller.ModalState

@Composable
fun GlobalModalHandler(
    modifier: Modifier = Modifier,
    controller: ModalController = LocalModalController.current
) {
    val modalState by controller.modalState

    when (val state = modalState) {
        is ModalState.WarningModal -> {
            DPlayScrim(
                backgroundColor = DPlayTheme.colors.dim80,
                onDismiss = state.onDismiss,
            )

            WarningModal(
                mainText = state.mainText,
                subText = state.subText,
                leftButtonLabel = state.leftButtonLabel,
                rightButtonLabel = state.rightButtonLabel,
                onLeftButtonClick = state.onLeftButtonClick,
                onRightButtonClick = state.onRightButtonClick,
                modifier = modifier
                    .padding(horizontal = 40.dp)
            )
        }
        is ModalState.GraphicModal -> {
            DPlayScrim(
                backgroundColor = DPlayTheme.colors.dim80,
                onDismiss = state.onDismiss,
            )

            GraphicModal(
                mainText = state.mainText,
                subText = state.subText,
                buttonLabel = state.buttonLabel,
                onCloseIconClick = state.onDismiss,
                onButtonClick = state.onButtonClick,
                modifier = modifier
                    .padding(horizontal = 40.dp)
            )
        }
        null -> { /* 다이얼로그 없음 */ }
    }
}