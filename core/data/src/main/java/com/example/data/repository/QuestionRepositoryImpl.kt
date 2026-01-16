package com.example.data.repository

import com.example.data.datasource.remote.QuestionRemoteDataSource
import com.example.data.mapper.todomain.toDomain
import com.example.domain.model.DailyQuestion
import com.example.domain.model.QuestionError
import com.example.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl
@Inject
constructor(
    private val questionRemoteDataSource: QuestionRemoteDataSource,
) : QuestionRepository {
    override suspend fun getQuestionRecord(
        year: Int,
        month: Int,
    ): Result<List<DailyQuestion>> =
        runCatching {
            val response =
                questionRemoteDataSource
                    .getQuestionRecord(year, month)
                    .data
                    ?: throw QuestionError.Unknown

            response.questions.map { it.toDomain() }
        }.recoverCatching { e ->
            if (e is retrofit2.HttpException) {
                throw when (e.code()) {
                    404 -> QuestionError.NotFound
                    else -> QuestionError.Unknown
                }
            } else {
                throw e
            }
        }
}