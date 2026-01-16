package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionRecordResponse(
    @SerialName("questions")
    val questions: List<QuestionItemResponse>,
)

@Serializable
data class QuestionItemResponse(
    @SerialName("day")
    val day: String,
    @SerialName("questionId")
    val questionId: Long,
    @SerialName("title")
    val title: String,
)
