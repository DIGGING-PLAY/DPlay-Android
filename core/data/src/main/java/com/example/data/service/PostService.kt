package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.POSTS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostDetailResponse
import com.example.data.model.response.PostLikeResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET("$API/$VERSIONS/$POSTS/detail/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
    ): BaseResponse<PostDetailResponse>

    @POST("$API/$VERSIONS/$POSTS/{postId}/likes")
    suspend fun postPostLike(
        @Path("postId") postId: Long,
    ): BaseResponse<PostLikeResponse>

    @DELETE("$API/$VERSIONS/$POSTS/{postId}/likes")
    suspend fun deletePostLike(
        @Path("postId") postId: Long,
    ): BaseResponse<PostLikeResponse>

    @POST("$API/$VERSIONS/$POSTS/{postId}/scraps")
    suspend fun postPostScrap(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>

    @DELETE("$API/$VERSIONS/$POSTS/{postId}/scraps")
    suspend fun deletePostScrap(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>

    @DELETE("$API/$VERSIONS/$POSTS/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>
}
