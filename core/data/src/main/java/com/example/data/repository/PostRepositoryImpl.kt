package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.remote.PostRemoteDataSource
import com.example.data.datasource.remote.QuestionPostsPagingSource
import com.example.data.mapper.todomain.toDomain
import com.example.data.model.request.RegisterPostRequest
import com.example.data.service.PostService
import com.example.domain.model.FeedItem
import com.example.domain.model.HomeScreenData
import com.example.domain.model.PostDetail
import com.example.domain.model.Track
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(
        private val postRemoteDataSource: PostRemoteDataSource,
        private val postService: PostService,
    ) : PostRepository {
        override suspend fun getPostDetail(postId: Long): Result<PostDetail> =
            runCatching {
                postRemoteDataSource.getPostDetail(postId = postId).data?.toDomain() ?: throw Exception()
            }

        override suspend fun registerPost(
            track: Track,
            comment: String,
        ): Result<Unit> =
            runCatching {
                postRemoteDataSource.registerPost(
                    request =
                        RegisterPostRequest(
                            trackId = track.trackId,
                            songTitle = track.songTitle,
                            artistName = track.artistName,
                            coverImg = track.coverImg,
                            isrc = track.isrc,
                            content = comment,
                        ),
                )
            }

        override suspend fun postPostLike(postId: Long): Result<Int> =
            runCatching {
                postRemoteDataSource
                    .postPostLike(postId = postId)
                    .data
                    ?.likeCount
                    ?: error("likeCount is null")
            }

        override suspend fun deletePostLike(postId: Long): Result<Int> =
            runCatching {
                postRemoteDataSource
                    .deletePostLike(postId = postId)
                    .data
                    ?.likeCount
                    ?: error("likeCount is null")
            }

        override suspend fun postPostScrap(postId: Long): Result<Unit> =
            runCatching {
                postRemoteDataSource.postPostScrap(postId = postId)
            }

        override suspend fun deletePostScrap(postId: Long): Result<Unit> =
            runCatching {
                postRemoteDataSource.deletePostScrap(postId = postId)
            }

        override suspend fun deletePost(postId: Long): Result<Unit> =
            runCatching {
                postRemoteDataSource.deletePost(postId = postId)
            }

        override suspend fun getTodayPosts(): Result<HomeScreenData> =
            runCatching {
                postRemoteDataSource.getTodayPosts().data?.toDomain() ?: throw Exception()
            }

        override fun getPostsByQuestionId(
            questionId: Long,
            onTotalCountFetched: (Int) -> Unit,
            onLockedFetched: (Boolean) -> Unit,
        ): Flow<PagingData<FeedItem>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = QUESTION_POSTS_LOAD_SIZE,
                        initialLoadSize = QUESTION_POSTS_LOAD_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    QuestionPostsPagingSource(
                        postService = postService,
                        questionId = questionId,
                        onTotalCountFetched = onTotalCountFetched,
                        onLockedFetched = onLockedFetched,
                    )
                },
            ).flow.map { pagingData ->
                pagingData.map { post ->
                    post.toDomain()
                }
            }

        companion object {
            const val QUESTION_POSTS_LOAD_SIZE = 20
        }
    }
