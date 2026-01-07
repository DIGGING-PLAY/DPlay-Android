package com.example.domain.repository

interface AuthRepository {
    suspend fun kakaoLogin(): Result<String>

    suspend fun signupWithKakao(
        profileImage: String?,
        nickname: String,
    ): Result<Unit>
}