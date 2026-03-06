package com.example.data.model.response

import com.example.domain.model.DailyQuestion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayQuestionResponse(
    @SerialName("questionId") val questionId: Long,
    @SerialName("date") val date: String,
    @SerialName("title") val title: String,
) {
    fun toEntity(): DailyQuestion =
        DailyQuestion(
            questionId = questionId,
            title = title,
            date = date,
        )
}
