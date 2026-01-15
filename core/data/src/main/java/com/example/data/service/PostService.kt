package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.POSTS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.request.RegisterPostRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.Body
import retrofit2.http.POST

@OptIn(InternalSerializationApi::class)
interface PostService {
    @POST("$API/$VERSIONS/$POSTS")
    suspend fun registerPost(
        @Body request: RegisterPostRequest
    ): BaseResponse<PostResponse>
}