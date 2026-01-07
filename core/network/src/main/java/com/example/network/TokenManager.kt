package com.example.network

interface TokenManager {
    suspend fun getAccessToken(): String?
}