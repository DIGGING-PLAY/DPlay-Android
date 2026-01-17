package com.example.domain.repository

import com.example.domain.model.TrackPreview

interface TrackRepository {
    suspend fun getTrackPreview(
        trackId: String,
        storefront: String? = null,
    ): Result<TrackPreview>
}
