package com.example.domain.repository

import com.example.domain.model.NicknameValidationResult

interface AuthRepository {
    suspend fun kakaoLogin(): Result<String>

    suspend fun signupWithKakao(
        kakaoAccessToken: String?,
        profileImage: String?,
        nickname: String,
    ): Result<NicknameValidationResult>

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>
}