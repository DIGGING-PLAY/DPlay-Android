package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.POSTS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostDetailResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path

@OptIn(InternalSerializationApi::class)
interface PostService {
    @GET("${API}/${VERSIONS}/${POSTS}/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
    ): BaseResponse<PostDetailResponse>
}
    @POST("$API/$VERSIONS/$POSTS/{postId}/likes")
    suspend fun postPostLike(
        @Path("postId") postId: Long,
    ): BaseResponse<PostLikeResponse>

    @DELETE("$API/$VERSIONS/$POSTS/{postId}/likes")
    suspend fun deletePostLike(
        @Path("postId") postId: Long,
    ): BaseResponse<PostLikeResponse>

