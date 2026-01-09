package com.example.data.repository

import com.example.data.constant.ApiConstants.KAKAO_PLATFORM
import com.example.data.constant.ErrorCode
import com.example.data.datasource.local.FileLocalDataSource
import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.remote.AuthRemoteDataSource
import com.example.data.datasource.remote.KakaoLoginDataSource
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.domain.repository.AuthRepository
import com.example.network.NetworkException
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class AuthRepositoryImpl
    @Inject
    constructor(
        private val kakaoDataSource: KakaoLoginDataSource,
        private val authRemoteDataSource: AuthRemoteDataSource,
        private val tokenLocalDataSource: TokenLocalDataSource,
        private val fileLocalDataSource: FileLocalDataSource,
    ) : AuthRepository {
        override suspend fun kakaoLogin(): Result<String> =
            runCatching {
                val kakaoToken = kakaoDataSource.getKakaoAccessToken()

                val tokenData =
                    try {
                        authRemoteDataSource.login(kakaoToken, LoginRequest(KAKAO_PLATFORM))
                    } catch (e: NetworkException) {
                        if (e.code == ErrorCode.EXPIRED_ACCESS_TOKEN) {
                            return Result.success(kakaoToken)
                        }
                        throw e
                    }

                tokenLocalDataSource.saveTokens(
                    accessToken = tokenData.accessToken,
                    refreshToken = tokenData.refreshToken,
                )

                return Result.success("")
            }

        override suspend fun signupWithKakao(
            kakaoAccessToken: String?,
            profileImage: String?,
            nickname: String,
        ): Result<Unit> =
            runCatching {
                val profileFile = fileLocalDataSource.getFileFromUri(profileImage)

                authRemoteDataSource.signup(
                    kakaoAccessToken = kakaoAccessToken,
                    imageFile = profileFile,
                    signupRequest =
                        SignupRequest(
                            platform = KAKAO_PLATFORM,
                            nickname = nickname,
                        ),
                )

                // 유저 정보 저장 구현
            }

        override suspend fun withdraw(): Result<Unit> =
            runCatching {
                authRemoteDataSource.withdraw()
                tokenLocalDataSource.clearTokens()
            }

        override suspend fun logout(): Result<Unit> =
            runCatching {
                authRemoteDataSource.logout()
                tokenLocalDataSource.clearTokens()
            }
    }
