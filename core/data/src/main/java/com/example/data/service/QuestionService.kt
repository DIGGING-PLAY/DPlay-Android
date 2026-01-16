package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.QUESTIONS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.QuestionRecordResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionService {

    @GET("$API/$VERSIONS/$QUESTIONS")
    suspend fun getQuestionRecord(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): BaseResponse<QuestionRecordResponse>
}
