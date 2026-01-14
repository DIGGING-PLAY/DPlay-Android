package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.TrackInfo
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun searchTracks(query: String): Flow<PagingData<TrackInfo>>
}