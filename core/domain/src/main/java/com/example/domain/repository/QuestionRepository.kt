package com.example.domain.repository

import com.example.domain.model.DailyQuestion

interface QuestionRepository {
    suspend fun getQuestionRecord(
        year: Int,
        month: Int,
    ): Result<List<DailyQuestion>>
}
