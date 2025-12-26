package com.example.ui.mapper

import com.example.designsystem.component.textfield.type.InputState
import com.example.designsystem.component.textfield.type.NicknameInputState
import com.example.domain.model.NicknameValidationResult

fun NicknameValidationResult.toUiState(): InputState =
    when (this) {
        NicknameValidationResult.Success -> NicknameInputState.Success
        NicknameValidationResult.Error.TooShort -> NicknameInputState.Error.NotEnoughLength
        NicknameValidationResult.Error.InvalidFormat -> NicknameInputState.Error.InvalidFormat
    }
