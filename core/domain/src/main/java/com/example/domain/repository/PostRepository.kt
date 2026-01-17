package com.example.domain.repository

import com.example.domain.model.HomeScreenData
import com.example.domain.model.PostDetail

interface PostRepository {
    suspend fun getPostDetail(postId: Long): Result<PostDetail>

    suspend fun postPostLike(
        postId: Long,
    ): Result<Int>

    suspend fun deletePostLike(
        postId: Long,
    ): Result<Int>

    suspend fun postPostScrap(
        postId: Long,
    ): Result<Unit>

    suspend fun deletePostScrap(
        postId: Long,
    ): Result<Unit>

    suspend fun deletePost(
        postId: Long,
    ): Result<Unit>

    suspend fun getTodayPosts(
    ): Result<HomeScreenData>
}