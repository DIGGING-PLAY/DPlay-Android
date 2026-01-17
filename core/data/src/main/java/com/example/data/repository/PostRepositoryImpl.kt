package com.example.data.repository

import com.example.data.datasource.remote.PostRemoteDataSource
import com.example.data.mapper.todomain.toDomain
import com.example.data.model.request.RegisterPostRequest
import com.example.domain.model.PostDetail
import com.example.domain.model.Track
import com.example.domain.repository.PostRepository
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class PostRepositoryImpl
    @Inject
    constructor(
        private val postRemoteDataSource: PostRemoteDataSource,
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
    }
