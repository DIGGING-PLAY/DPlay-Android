package com.example.network

interface TokenManager {
    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun updateAccessToken(newAccessToken: String)

    suspend fun clearAllTokens()
}
