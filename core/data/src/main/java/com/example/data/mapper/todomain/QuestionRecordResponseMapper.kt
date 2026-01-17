package com.example.data.mapper.todomain

import com.example.data.model.response.QuestionItemResponse
import com.example.domain.model.DailyQuestion

fun QuestionItemResponse.toDomain(year: Int, month: Int): DailyQuestion =
    DailyQuestion(
        questionId = questionId,
        title = title,
        date = this.day,
        year = year,
        month = month,
    )
