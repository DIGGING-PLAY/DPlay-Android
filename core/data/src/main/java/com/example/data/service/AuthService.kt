package com.example.data.service

import com.example.data.ApiConstants.API
import com.example.data.ApiConstants.AUTH
import com.example.data.ApiConstants.LOGIN
import com.example.data.ApiConstants.LOGOUT
import com.example.data.ApiConstants.REISSUE
import com.example.data.ApiConstants.SIGNUP
import com.example.data.ApiConstants.TOKEN
import com.example.data.ApiConstants.VERSIONS
import com.example.data.ApiConstants.WITHDRAW
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TokenResponse
import kotlinx.serialization.InternalSerializationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

@OptIn(InternalSerializationApi::class)
interface AuthService {
    @POST("$API/$VERSIONS/$AUTH/$LOGIN")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body request: LoginRequest
    ): BaseResponse<TokenResponse>

    @Multipart
    @POST("$API/$VERSIONS/$AUTH/$SIGNUP")
    suspend fun signup(
        @Header("Authorization") accessToken: String,
        @Part profileImg: MultipartBody.Part?,
        @Part("signupRequest") request: RequestBody
    ): BaseResponse<TokenResponse>

    @PATCH("$API/$VERSIONS/$AUTH/$TOKEN/$REISSUE")
    suspend fun reissue(
        @Header("Authorization") refreshToken: String
    ): BaseResponse<TokenResponse>

    @DELETE("$API/$VERSIONS/$AUTH/$LOGOUT")
    suspend fun logout(): BaseResponse<Unit>

    @DELETE("$API/$VERSIONS/$AUTH/$WITHDRAW")
    suspend fun withdraw(): BaseResponse<Unit>
}