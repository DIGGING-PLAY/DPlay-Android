package com.example.data.repository

import com.example.data.datasource.local.TokenLocalDataSource
import com.example.data.datasource.remote.AuthRemoteDataSource
import com.example.data.datasource.remote.KakaoLoginDataSource
import com.example.data.model.request.LoginRequest
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
    ): AuthRepository {
        override suspend fun kakaoLogin(): Result<String> = runCatching {
            val kakaoToken = kakaoDataSource.getKakaoAccessToken()

            val tokenData = try {
                authRemoteDataSource.login(kakaoToken, LoginRequest("KAKAO"))
            } catch (e: NetworkException) {

                if (e.code == 4041) {
                    return Result.success("회원가입 필요")
                }
                throw e
            }

            tokenLocalDataSource.saveTokens(
                accessToken = tokenData.accessToken,
                refreshToken = tokenData.refreshToken
            )

            return Result.success("로그인 성공")
        }
}