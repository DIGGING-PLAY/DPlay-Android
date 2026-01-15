package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.response.ScrappedTrackResponse
import com.example.data.service.UserService
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
class ScrappedTracksPagingSource(
    private val userService: UserService,
    private val userId: Long,
): PagingSource<String, ScrappedTrackResponse>() {
    override fun getRefreshKey(state: PagingState<String, ScrappedTrackResponse>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ScrappedTrackResponse> {
        return try {
            val currentCursor = params.key

            val response = userService.getScrappedTracks(
                userId = userId,
                page = currentCursor,
                size = null
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