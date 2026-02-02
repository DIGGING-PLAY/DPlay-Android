package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.FeedItem
import com.example.domain.model.HomeScreenData
import com.example.domain.model.Track
import com.example.domain.model.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun registerPost(
        track: Track,
        comment: String,
    ): Result<Unit>

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

    fun getPostsByQuestionId(
        questionId: Long,
        onTotalCountFetched: (Int) -> Unit,
        onLockedFetched: (Boolean) -> Unit,
    ): Flow<PagingData<FeedItem>>
}