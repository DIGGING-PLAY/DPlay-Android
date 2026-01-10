package com.example.data.repository

import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.local.UserLocalDataSource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userLocalDataSource: UserLocalDataSource,
        private val tokenLocalDataSource: TokenLocalDataSource,
    ) : UserRepository {
        override fun getUser(): Flow<User?> = userLocalDataSource.userFlow

        override fun getAccessToken(): Flow<String?> = tokenLocalDataSource.accessToken

        override fun getRefreshToken(): Flow<String?> = tokenLocalDataSource.refreshToken
    }
