package com.example.designsystem.component.textfield.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.dplay.designsystem.R
import com.example.designsystem.component.textfield.constant.TextFieldConstant

sealed class InputState {
    data object Default : InputState()

    sealed class Error : InputState() {
        @Composable
        abstract fun getMessage(): String
    }

    sealed class Success : InputState() {
        @Composable
        abstract fun getMessage(): String
    }
}

@Immutable
sealed class NicknameInputState : InputState() {
    sealed class Error : InputState.Error() {
        data object NotEnoughLength : Error(){
            @Composable
            override fun getMessage(): String = stringResource(R.string.nickname_error_not_enough_length,TextFieldConstant.MIN_NICKNAME_LENGTH)
        }

        data object InvalidFormat: Error() {
            @Composable
            override fun getMessage(): String = stringResource(R.string.nickname_error_invalid_format)
        }

        data object AlreadyExists: Error() {
            @Composable
            override fun getMessage(): String = stringResource(R.string.nickname_error_already_exists)
        }

        data object ForbiddenWord : Error() {
            @Composable
            override fun getMessage(): String = stringResource(R.string.nickname_error_forbidden_word)
        }
    }

    data object Success : InputState.Success() {
        @Composable
        override fun getMessage(): String = stringResource(R.string.nickname_success_message)
    }
}