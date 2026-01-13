package com.example.domain.repository

interface AuthRepository {
    suspend fun kakaoLogin(): Result<String>

    suspend fun signupWithKakao(
        kakaoAccessToken: String?,
        profileImage: String?,
        nickname: String,
    ): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>
}