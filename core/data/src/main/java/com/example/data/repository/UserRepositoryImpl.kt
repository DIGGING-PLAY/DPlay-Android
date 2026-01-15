package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.FileLocalDataSource
import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.RegisteredTracksPagingSource
import com.example.data.datasource.remote.TrackPagingSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.service.UserService
import com.example.domain.model.ProfileImageState
import com.example.domain.model.RegisteredTrack
import com.example.domain.model.ScrappedTrack
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userLocalDataSource: UserLocalDataSource,
        private val tokenLocalDataSource: TokenLocalDataSource,
        private val userRemoteDataSource: UserRemoteDataSource,
        private val fileLocalDataSource: FileLocalDataSource,
        private val userService: UserService,
    ) : UserRepository {
        override fun getUser(): Flow<User?> =
            userLocalDataSource.userFlow.map { user ->
                user?.let { validUser ->
                    if (validUser.profileImagePath?.isEmpty() == true) {
                        validUser.copy(profileImagePath = null)
                    } else {
                        validUser
                    }
                }
            }

        override fun getAccessToken(): Flow<String?> = tokenLocalDataSource.accessToken

        override fun getRefreshToken(): Flow<String?> = tokenLocalDataSource.refreshToken

        override suspend fun getNotificationEnabled(): Result<Boolean> =
            runCatching {
                userRemoteDataSource.getNotificationEnabled()
            }

        override suspend fun updateNotificationEnabled(enabled: Boolean): Result<Unit> =
            runCatching {
                userRemoteDataSource.postNotificationEnabled(enabled)
            }

        override suspend fun updateProfile(
            nickname: String?,
            profileImageState: ProfileImageState,
        ): Result<Unit> =
            runCatching {
                val profileFile =
                    when (profileImageState) {
                        is ProfileImageState.Keep -> null
                        is ProfileImageState.Delete -> fileLocalDataSource.createEmptyFile()
                        is ProfileImageState.Update -> fileLocalDataSource.createAndGetFile(profileImageState.imagePath)
                    }

                userRemoteDataSource.patchProfile(
                    imageFile = profileFile,
                    nickname = nickname,
                )

                userLocalDataSource.updateNickname(nickname.orEmpty())

                when (profileImageState) {
                    ProfileImageState.Delete -> {
                        userLocalDataSource.removeProfileImage()
                    }
                    is ProfileImageState.Update -> {
                        userLocalDataSource.updateProfileImage(
                            profileImagePath = profileFile?.absolutePath ?: "",
                        )
                    }
                    ProfileImageState.Keep -> {}
                }
            }

        override fun getRegisteredTracks(
            userId: Long,
            onTotalCountFetched: (Int) -> Unit
        ): Flow<PagingData<RegisteredTrack>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                ),
                pagingSourceFactory = { RegisteredTracksPagingSource(
                    userService = userService,
                    userId = userId,
                    onTotalCountFetched = onTotalCountFetched
                ) }
            ).flow.map { pagingData ->
                pagingData.map { track ->
                    track.toDomain()
                }
            }
        }

        override fun getScrappedTracks(): Flow<PagingData<ScrappedTrack>> {
            TODO("Not yet implemented")
        }
}
