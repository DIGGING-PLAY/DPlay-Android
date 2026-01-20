package com.example.data.repository

import com.example.data.constant.ApiConstants.KAKAO_PLATFORM
import com.example.data.constant.ErrorCode
import com.example.data.datasource.local.FileLocalDataSource
import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.remote.AuthRemoteDataSource
import com.example.data.datasource.remote.KakaoLoginDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.domain.model.NicknameValidationResult
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import com.example.network.NetworkException
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val kakaoDataSource: KakaoLoginDataSource,
        private val authRemoteDataSource: AuthRemoteDataSource,
        private val tokenLocalDataSource: TokenLocalDataSource,
        private val fileLocalDataSource: FileLocalDataSource,
        private val userLocalDataSource: UserLocalDataSource,
        private val userRemoteDataSource: UserRemoteDataSource,
    ) : AuthRepository {
        override suspend fun kakaoLogin(): Result<String> =
            runCatching {
                val kakaoToken = kakaoDataSource.getKakaoAccessToken()

                val tokenData =
                    try {
                        authRemoteDataSource.login(kakaoToken, LoginRequest(KAKAO_PLATFORM))
                    } catch (e: NetworkException) {
                        if (e.code == ErrorCode.USER_NOT_FOUND) {
                            return Result.success(kakaoToken)
                        }
                        throw e
                    }

                tokenLocalDataSource.saveTokens(
                    accessToken = tokenData.accessToken,
                    refreshToken = tokenData.refreshToken,
                )

                val userInfo =
                    userRemoteDataSource.getUser(
                        userId = tokenData.userId,
                    )

                userLocalDataSource.saveUser(
                    User(
                        id = tokenData.userId,
                        nickname = userInfo.user.nickname,
                        profileImagePath = userInfo.user.profileImg,
                    ),
                )

                return Result.success("")
            }

        override suspend fun signupWithKakao(
            kakaoAccessToken: String?,
            profileImage: String?,
            nickname: String,
        ): Result<NicknameValidationResult> =
            runCatching {
                val profileFile = fileLocalDataSource.createAndGetFile(profileImage)

                val response =
                    try {
                        authRemoteDataSource.signup(
                            kakaoAccessToken = kakaoAccessToken,
                            imageFile = profileFile,
                            signupRequest =
                                SignupRequest(
                                    platform = KAKAO_PLATFORM,
                                    nickname = nickname,
                                ),
                        )
                    } catch(e: NetworkException){
                        if (e.code == ErrorCode.DUPLICATED_NICKNAME) {
                            return Result.success(NicknameValidationResult.Error.Duplicated)
                        }
                        throw e
                    }
                tokenLocalDataSource.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )

                userLocalDataSource.saveUser(
                    User(
                        id = response.userId,
                        nickname = nickname,
                        profileImagePath = profileFile?.absolutePath,
                    ),
                )

                return Result.success(NicknameValidationResult.Success)
            }

        override suspend fun withdraw(): Result<Unit> =
            runCatching {
                authRemoteDataSource.withdraw()
                tokenLocalDataSource.clearTokens()
                userLocalDataSource.clearUser()
            }

        override suspend fun logout(): Result<Unit> =
            runCatching {
                authRemoteDataSource.logout()
                tokenLocalDataSource.clearTokens()
                userLocalDataSource.clearUser()
            }
    }
