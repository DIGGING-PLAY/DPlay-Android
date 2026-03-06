package com.example.domain.repository

import com.example.domain.model.TrackPreview
import androidx.paging.PagingData
import com.example.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun searchTracks(query: String): Flow<PagingData<Track>>

    suspend fun getTrack(trackId: String): Result<Track>

    suspend fun getTrackPreview(
        trackId: String,
        storefront: String? = null,
    ): Result<TrackPreview>
}