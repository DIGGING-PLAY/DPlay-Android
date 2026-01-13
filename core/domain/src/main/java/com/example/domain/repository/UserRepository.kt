package com.example.domain.repository

import com.example.domain.model.ProfileImageState
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun updateProfile(
        nickname: String?,
        profileImageState: ProfileImageState,
    ): Result<Unit>
}