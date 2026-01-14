package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.remote.TrackPagingSource
import com.example.data.service.TrackService
import com.example.domain.model.Track
import com.example.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrackRepositoryImpl
@Inject constructor(
    private val trackService: TrackService
): TrackRepository {
    override fun searchTracks(query: String): Flow<PagingData<Track>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { TrackPagingSource(trackService, query) }
        ).flow.map { pagingData ->
            pagingData.map { track ->
                track.toDomain()
            }
        }
    }
}