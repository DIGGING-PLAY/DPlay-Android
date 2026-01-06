package com.example.data.datasource.remote

import com.example.data.model.request.LoginRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TokenResponse
import com.example.data.service.AuthService
import com.example.network.NetworkException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class AuthRemoteDataSource
@Inject
constructor(
    private val authService: AuthService,
) {
    suspend fun login(
        accessToken: String,
        loginRequest: LoginRequest,
    ): TokenResponse {
        try {
            val response = authService.login(
                accessToken = accessToken,
                request = loginRequest,
            )

            return response.data ?: throw Exception("Data is null")

        } catch (e: HttpException){
            if(e.code() == 404){
                val errorString = e.response()?.errorBody()?.string()

                if (errorString != null) {
                    try {
                        val errorResponse = Json.decodeFromString<BaseResponse<String?>>(errorString)

                        if (errorResponse.code == 4041) {
                            throw NetworkException(4041, errorResponse.message)
                        }
                    } catch (e: SerializationException) {
                        // JSON 형식이 잘못됨 (괄호 누락 등)
                    } catch (e: IllegalArgumentException) {
                        // 데이터 타입 불일치
                    }
                }
            }

            throw e
        }
    }
}