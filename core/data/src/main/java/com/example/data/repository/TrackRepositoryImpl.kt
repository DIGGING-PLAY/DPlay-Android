package com.example.data.repository

import com.example.data.datasource.remote.TrackRemoteDataSource
import com.example.data.mapper.todomain.toDomain
import com.example.domain.model.TrackPreview
import com.example.domain.repository.TrackRepository
import javax.inject.Inject

class TrackRepositoryImpl
    @Inject
    constructor(
        private val trackRemoteDataSource: TrackRemoteDataSource,
    ) : TrackRepository {
        override suspend fun getTrackPreview(
            trackId: String,
            storefront: String?,
        ): Result<TrackPreview> =
            runCatching {
                trackRemoteDataSource
                    .getTrackPreview(trackId = trackId, storefront = storefront)
                    .data
                    ?.toDomain()
                    ?: error("TrackPreview data is null")
            }
    }
