package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.POSTS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.request.RegisterPostRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostDetailResponse
import com.example.data.model.response.PostLikeResponse
import com.example.data.model.response.PostResponse
import com.example.data.model.response.QuestionPostsResponse
import com.example.data.model.response.TodayPostsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @POST("$API/$VERSIONS/$POSTS")
    suspend fun registerPost(
        @Body request: RegisterPostRequest,
    ): BaseResponse<PostResponse>

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

    @GET("$API/$VERSIONS/$POSTS/today")
    suspend fun getTodayPosts(): BaseResponse<TodayPostsResponse>

    @GET("$API/$VERSIONS/$POSTS/{questionId}")
    suspend fun getPostsByQuestionId(
        @Path("questionId") questionId: Long,
        @Query("cursor") cursor: String?,
        @Query("limit") limit: Int,
    ): BaseResponse<QuestionPostsResponse>
}
