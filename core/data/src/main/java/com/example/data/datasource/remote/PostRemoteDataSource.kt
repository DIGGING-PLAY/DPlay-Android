package com.example.data.datasource.remote

import com.example.data.model.request.RegisterPostRequest
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostDetailResponse
import com.example.data.model.response.PostLikeResponse
import com.example.data.model.response.TodayPostsResponse
import com.example.data.model.response.PostResponse
import com.example.data.service.PostService
import javax.inject.Inject

class PostRemoteDataSource
    @Inject
    constructor(
        private val postService: PostService,
    ) {
        suspend fun getPostDetail(postId: Long): BaseResponse<PostDetailResponse> = postService.getPostDetail(postId = postId)

        suspend fun postPostLike(
            postId: Long,
        ): BaseResponse<PostLikeResponse> = postService.postPostLike(postId = postId)

        suspend fun deletePostLike(
            postId: Long,
        ): BaseResponse<PostLikeResponse> = postService.deletePostLike(postId = postId)

        suspend fun postPostScrap(
            postId: Long,
        ): BaseResponse<Unit> = postService.postPostScrap(postId = postId)

        suspend fun deletePostScrap(
            postId: Long,
        ): BaseResponse<Unit> = postService.deletePostScrap(postId = postId)

        suspend fun deletePost(
            postId: Long,
        ): BaseResponse<Unit> = postService.deletePost(postId = postId)

        suspend fun registerPost(
            request: RegisterPostRequest,
        ): PostResponse {
            try {
                val response =
                    postService.registerPost(
                        request = request,
                    )
                return response.data ?: throw Exception("Data is null")
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun getTodayPosts(): BaseResponse<TodayPostsResponse> = postService.getTodayPosts()
    }
