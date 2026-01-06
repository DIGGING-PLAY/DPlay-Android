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
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TokenResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

@OptIn(InternalSerializationApi::class)
interface AuthService {
    @POST("$API/$VERSIONS/$AUTH/$LOGIN")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body request: LoginRequest
    ): BaseResponse<TokenResponse>

    @POST("$API/$VERSIONS/$AUTH/$SIGNUP")
    suspend fun signup(): BaseResponse<TokenResponse>

    @PATCH("$API/$VERSIONS/$AUTH/$TOKEN/$REISSUE")
    suspend fun reissue(): BaseResponse<TokenResponse>

    @DELETE("$API/$VERSIONS/$AUTH/$LOGOUT")
    suspend fun logout(): BaseResponse<Unit>

    @DELETE("$API/$VERSIONS/$AUTH/$WITHDRAW")
    suspend fun withdraw(): BaseResponse<Unit>
}