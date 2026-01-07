package com.example.data.repository

import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.remote.AuthRemoteDataSource
import com.example.data.datasource.remote.KakaoLoginDataSource
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.domain.repository.AuthRepository
import com.example.network.NetworkException
import kotlinx.serialization.InternalSerializationApi
import java.io.File
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class AuthRepositoryImpl
    @Inject
    constructor(
        private val kakaoDataSource: KakaoLoginDataSource,
        private val authRemoteDataSource: AuthRemoteDataSource,
        private val tokenLocalDataSource: TokenLocalDataSource,
    ): AuthRepository {
        override suspend fun kakaoLogin(): Result<String> = runCatching {
            val kakaoToken = kakaoDataSource.getKakaoAccessToken()

            val tokenData = try {
                authRemoteDataSource.login(kakaoToken, LoginRequest("KAKAO"))
            } catch (e: NetworkException) {

                if (e.code == 4041) {
                    return Result.success(kakaoToken)
                }
                throw e
            }

            tokenLocalDataSource.saveTokens(
                accessToken = tokenData.accessToken,
                refreshToken = tokenData.refreshToken
            )

            return Result.success("")
        }

    override suspend fun signupWithKakao(
        profileImage: File?,
        nickname: String,
    ): Result<Unit> =
        runCatching{
            authRemoteDataSource.signup(
                imageFile = profileImage,
                signupRequest = SignupRequest(
                    platform = "KAKAO",
                    nickname = nickname,
                )
            )
        }
}