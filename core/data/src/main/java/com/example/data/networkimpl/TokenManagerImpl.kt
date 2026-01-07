package com.example.data.networkimpl

import com.example.data.datasource.local.TokenLocalDataSource
import com.example.network.TokenManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenManager {

    override suspend fun getAccessToken(): String? {
        return tokenLocalDataSource.accessToken.first()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenLocalDataSource.refreshToken.first()
    }

    override suspend fun updateAccessToken(newAccessToken: String) {
        tokenLocalDataSource.updateAccessToken(newAccessToken)
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenLocalDataSource.saveTokens(accessToken, refreshToken)
    }

    override suspend fun clearAllTokens() {
        tokenLocalDataSource.clearTokens()
    }
}