package com.example.domain.repository

import java.io.File

interface AuthRepository {
    suspend fun kakaoLogin(): Result<String>

    suspend fun signupWithKakao(
        profileImage: File?,
        nickname: String,
    ): Result<Unit>
}