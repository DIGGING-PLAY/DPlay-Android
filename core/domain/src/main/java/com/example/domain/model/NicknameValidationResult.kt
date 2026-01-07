package com.example.domain.model

sealed interface NicknameValidationResult {
    data object Success : NicknameValidationResult

    sealed interface Error : NicknameValidationResult {
        data object TooShort : Error
        data object InvalidFormat : Error
    }
}