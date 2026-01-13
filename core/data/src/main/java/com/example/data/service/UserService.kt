package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.ME
import com.example.data.constant.ApiConstants.NOTIFICATIONS
import com.example.data.constant.ApiConstants.USERS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.NotificationResponse
import com.example.data.model.response.UserResponse
import kotlinx.serialization.InternalSerializationApi
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

@OptIn(InternalSerializationApi::class)
interface UserService {
    @Multipart
    @PATCH("$API/$VERSIONS/$USERS/$ME")
    suspend fun patchProfile(
        @Part profileImg: MultipartBody.Part?,
        @Part("nickname") request: String?,
    ): BaseResponse<Unit>

    @GET("$API/$VERSIONS/$USERS/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long,
    ): BaseResponse<UserResponse>

    @GET("$API/$VERSIONS/$USERS/$ME/$NOTIFICATIONS")
    suspend fun getNotificationEnabled(): BaseResponse<NotificationResponse>
}