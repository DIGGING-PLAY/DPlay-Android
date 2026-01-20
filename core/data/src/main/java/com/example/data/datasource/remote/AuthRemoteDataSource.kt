package com.example.data.datasource.remote

import com.example.data.constant.ErrorCode
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TokenResponse
import com.example.data.service.AuthService
import com.example.network.NetworkException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthRemoteDataSource
    @Inject
    constructor(
        private val authService: AuthService,
        private val json: Json,
    ) {
        suspend fun login(
            accessToken: String,
            loginRequest: LoginRequest,
        ): TokenResponse {
            try {
                val response =
                    authService.login(
                        accessToken = accessToken,
                        request = loginRequest,
                    )

                return response.data ?: throw Exception("Data is null")
            } catch (e: HttpException) {
                if (e.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    val errorString = e.response()?.errorBody()?.string()

                    if (errorString != null) {
                        try {
                            val errorResponse = json.decodeFromString<BaseResponse<String?>>(errorString)

                            if (errorResponse.code == ErrorCode.USER_NOT_FOUND) {
                                throw NetworkException(ErrorCode.USER_NOT_FOUND, errorResponse.message)
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

        suspend fun signup(
            kakaoAccessToken: String?,
            imageFile: File?,
            signupRequest: SignupRequest,
        ): TokenResponse {
            try {
                val imagePart =
                    if (imageFile != null) {
                        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("profileImg", imageFile.name, requestFile)
                    } else {
                        null
                    }

                val jsonString = json.encodeToString(signupRequest)
                val requestPart = jsonString.toRequestBody("application/json".toMediaType())

                val response =
                    authService.signup(
                        accessToken = kakaoAccessToken ?: "",
                        profileImg = imagePart,
                        request = requestPart,
                    )

                return response.data ?: throw Exception("Data is null")
            } catch (e: HttpException) {
                if(e.code() == HttpURLConnection.HTTP_CONFLICT){
                    val errorString = e.response()?.errorBody()?.string()
                    Timber.d("errorString : $errorString")

                    if (errorString != null) {
                        try {
                            val errorResponse = json.decodeFromString<BaseResponse<String?>>(errorString)
                            Timber.d("errorResponse : $errorResponse")
                            if (errorResponse.code == ErrorCode.DUPLICATED_NICKNAME) {
                                throw NetworkException(ErrorCode.DUPLICATED_NICKNAME, errorResponse.message)
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

        suspend fun logout() {
            try {
                authService.logout()
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun withdraw() {
            try {
                authService.withdraw()
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun reissue(refreshToken: String): TokenResponse {
            try {
                val response = authService.reissue(refreshToken = refreshToken)
                return response.data ?: throw Exception("Data is null")
            } catch (e: Exception) {
                throw e
            }
        }
    }
