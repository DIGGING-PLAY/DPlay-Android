package com.example.data

import com.example.data.datasource.remote.AuthRemoteDataSource
import com.example.data.model.response.TokenResponse
import com.example.network.TokenManager
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.InternalSerializationApi
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

@OptIn(InternalSerializationApi::class)
class TokenAuthenticator
    @Inject
    constructor(
        private val tokenManager: TokenManager,
        private val authDataSourceProvider: Provider<AuthRemoteDataSource>,
    ) : Authenticator {
        private val mutex = Mutex()

        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            if (response.retryCount >= MAX_RETRY) return null

            return runBlocking {
                mutex.withLock {
                    tryWithExistingToken(response) ?: refreshAndRetry(response)
                }
            }
        }

        private val Response.retryCount: Int
            get() = generateSequence(priorResponse) { it.priorResponse }.count()

        private suspend fun tryWithExistingToken(response: Response): Request? {
            val currentToken = tokenManager.getAccessToken()
            val requestToken =
                response.request
                    .header("Authorization")
                    ?.removePrefix("Bearer ")

            return if (currentToken != requestToken && currentToken != null) {
                response.request
                    .newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            } else {
                null
            }
        }

        private suspend fun refreshAndRetry(response: Response): Request? {
            val refreshToken = tokenManager.getRefreshToken() ?: return null

            return try {
                val newTokens = getNewTokens(refreshToken) ?: return null
                tokenManager.saveTokens(newTokens.accessToken, newTokens.refreshToken)

                response.request
                    .newBuilder()
                    .header("Authorization", "Bearer ${newTokens.accessToken}")
                    .build()
            } catch (e: Exception) {
                tokenManager.clearAllTokens()
                null
            }
        }

        private suspend fun getNewTokens(refreshToken: String): TokenResponse? =
            try {
                authDataSourceProvider.get().reissue(refreshToken = refreshToken)
            } catch (e: Exception) {
                null
            }

        companion object {
            private const val MAX_RETRY = 2
        }
    }
