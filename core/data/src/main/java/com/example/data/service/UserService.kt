package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.PATCH_PROFILE
import com.example.data.constant.ApiConstants.USERS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import kotlinx.serialization.InternalSerializationApi
import okhttp3.MultipartBody
import retrofit2.http.PATCH
import retrofit2.http.Part

@OptIn(InternalSerializationApi::class)
interface UserService {
    @PATCH("$API/$VERSIONS/$USERS/$PATCH_PROFILE")
    suspend fun patchProfile(
        @Part profileImg: MultipartBody.Part?,
        @Part("nickname") request: String?,
    ): BaseResponse<Unit>
}