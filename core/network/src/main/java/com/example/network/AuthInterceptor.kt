package com.example.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            if (originalRequest.header("Authorization") != null) {
                return chain.proceed(originalRequest)
            }

            val accessToken = runBlocking { tokenManager.getAccessToken() }

            val newRequest =
                originalRequest
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()

            return chain.proceed(newRequest)
        }
    }
