package com.example.data.repository

import com.example.data.datasource.remote.PostRemoteDataSource
import com.example.data.mapper.todomain.toDomain
import com.example.domain.model.FeedItem
import com.example.domain.model.PostDetail
import com.example.domain.repository.PostRepository
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class PostRepositoryImpl
    @Inject
    constructor(
        private val postRemoteDataSource: PostRemoteDataSource
    ) : PostRepository {
        override suspend fun getPostDetail(postId: Long): Result<PostDetail> =
            runCatching {
                postRemoteDataSource.getPostDetail(postId=postId).data?.toDomain() ?: throw Exception()
            }
    }
