package com.example.data.repository

import com.example.data.datasource.local.FileLocalDataSource
import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.domain.model.ProfileImageState
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
    ) : UserRepository {
        override fun getUser(): Flow<User?> = userLocalDataSource.userFlow.map{ user ->
            user?.let{ validUser ->
                if(validUser.profileImagePath?.isEmpty() == true){
                    validUser.copy(profileImagePath = null)
                }else{
                    validUser
                }
            }
        }

        override fun getAccessToken(): Flow<String?> = tokenLocalDataSource.accessToken

        override fun getRefreshToken(): Flow<String?> = tokenLocalDataSource.refreshToken

        override suspend fun updateProfile(
            nickname: String?,
            profileImageState: ProfileImageState
        ): Result<Unit> = runCatching {

            val profileFile = when(profileImageState){
                is ProfileImageState.Keep -> null
                is ProfileImageState.Delete -> fileLocalDataSource.createEmptyFile()
                is ProfileImageState.Update -> fileLocalDataSource.createAndGetFile(profileImageState.imagePath)
            }

            userRemoteDataSource.patchProfile(
                imageFile = profileFile,
                nickname = nickname,
            )

            userLocalDataSource.updateNickname(nickname.orEmpty())

            when(profileImageState){
                ProfileImageState.Delete -> {
                    userLocalDataSource.removeProfileImage()
                }
                is ProfileImageState.Update -> {
                    userLocalDataSource.updateProfileImage(
                        profileImagePath = profileFile?.absolutePath ?: ""
                    )
                }
                ProfileImageState.Keep -> {}
            }
        }
    }
