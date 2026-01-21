package com.example.data.datasource.remote

import com.example.data.constant.ErrorCode
import com.example.data.model.request.NotificationRequest
import com.example.data.model.request.PatchProfileRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.UserInfoResponse
import com.example.data.service.UserService
import com.example.network.NetworkException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class UserRemoteDataSource
    @Inject
    constructor(
        private val userService: UserService,
        private val json: Json,
    ) {
        suspend fun patchProfile(
            nickname: String?,
            imageFile: File?,
        ) {
            try {
                val imagePart =
                    if (imageFile != null) {
                        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("profileImg", imageFile.name, requestFile)
                    } else {
                        null
                    }

                userService.patchProfile(
                    profileImg = imagePart,
                    request =
                        PatchProfileRequest(
                            nickname,
                        ),
                )
            } catch (e: HttpException) {
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
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

        suspend fun getUser(
            userId: Long,
        ): UserInfoResponse {
            try {
                val response =
                    userService.getUser(
                        userId = userId,
                    )
                return response.data ?: throw Exception("Data is null")
            } catch (e: Exception) {
                Timber.e(e)
                throw e
            }
        }

        suspend fun getNotificationEnabled(): Boolean {
            try {
                val response = userService.getNotificationEnabled()
                return response.data?.pushOn ?: throw Exception("Data is null")
            } catch (e: Exception) {
                Timber.e(e)
                throw e
            }
        }

        suspend fun postNotificationEnabled(
            enabled: Boolean,
        ) {
            try {
                userService.postNotificationEnabled(
                    request =
                        NotificationRequest(
                            pushOn = enabled,
                        ),
                )
            } catch (e: Exception) {
                Timber.e(e)
                throw e
            }
        }
    }
