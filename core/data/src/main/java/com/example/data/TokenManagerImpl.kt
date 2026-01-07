package com.example.data

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
}