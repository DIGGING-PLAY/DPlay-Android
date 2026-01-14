package com.example.domain.repository

import com.example.domain.model.PostDetail

interface PostRepository {
    suspend fun getPostDetail(postId: Long): Result<PostDetail>
}