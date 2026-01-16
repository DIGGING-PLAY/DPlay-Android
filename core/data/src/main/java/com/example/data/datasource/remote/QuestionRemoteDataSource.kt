package com.example.data.datasource.remote

import com.example.data.model.response.BaseResponse
import com.example.data.model.response.QuestionRecordResponse
import com.example.data.service.QuestionService
import javax.inject.Inject

class QuestionRemoteDataSource
@Inject
constructor(
    private val questionService: QuestionService,
) {
    suspend fun getQuestionRecord(
        year: Int,
        month: Int,
    ): BaseResponse<QuestionRecordResponse> =
        questionService.getQuestionRecord(
            year = year,
            month = month,
        )
}
