package com.example.domain.model

sealed class QuestionError : Throwable() {
    object NotFound : QuestionError()
    object Unknown : QuestionError()
}