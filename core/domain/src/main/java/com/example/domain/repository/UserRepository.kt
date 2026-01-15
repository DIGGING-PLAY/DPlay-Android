package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.ProfileImageState
import com.example.domain.model.RegisteredTrack
import com.example.domain.model.ScrappedTrack
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun getNotificationEnabled(): Result<Boolean>

    suspend fun updateNotificationEnabled(enabled: Boolean): Result<Unit>

    suspend fun updateProfile(
        nickname: String?,
        profileImageState: ProfileImageState,
    ): Result<Unit>

    fun getRegisteredTracks(
        userId: Long,
        onTotalCountFetched: (Int) -> Unit
    ): Flow<PagingData<RegisteredTrack>>

    fun getScrappedTracks(): Flow<PagingData<ScrappedTrack>>
}