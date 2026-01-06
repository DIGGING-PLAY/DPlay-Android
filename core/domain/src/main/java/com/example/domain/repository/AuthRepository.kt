package com.example.domain.repository

interface AuthRepository {
    suspend fun kakaoLogin(): Result<String>
}