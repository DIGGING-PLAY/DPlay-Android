package com.example.data.repository

import com.example.data.datasource.remote.PostRemoteDataSource
import com.example.data.model.request.RegisterPostRequest
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
    }
