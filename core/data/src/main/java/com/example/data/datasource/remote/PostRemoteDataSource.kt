package com.example.data.datasource.remote

import com.example.data.model.response.BaseResponse
import com.example.data.model.response.PostDetailResponse
import com.example.data.service.PostService
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class PostRemoteDataSource
    @Inject
    constructor(
        private val postService: PostService,
    ) {
        suspend fun getPostDetail(postId: Long): BaseResponse<PostDetailResponse> = postService.getPostDetail(postId = postId)
    }
