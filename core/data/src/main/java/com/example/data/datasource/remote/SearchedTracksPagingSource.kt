package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.response.TrackResponse
import com.example.data.service.TrackService
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
class SearchedTracksPagingSource(
    private val trackService: TrackService,
    private val query: String,
): PagingSource<String, TrackResponse>() {
    override fun getRefreshKey(state: PagingState<String, TrackResponse>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TrackResponse> {
        return try {
            val currentCursor = params.key

            val response = trackService.searchTracks(
                query = query,
                limit = params.loadSize,
                storefront = null,
                cursor = currentCursor
            )

            val data = response.data ?: throw Exception("data is null")
            val tracks = data.items
            val nextCursor = data.nextCursor
            
            LoadResult.Page(
                data = tracks,
                prevKey = null,
                nextKey = if (tracks.isEmpty()) null else nextCursor
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}