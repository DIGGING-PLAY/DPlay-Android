package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.response.RegisteredTrackResponse
import com.example.data.model.response.RegisteredTracksResponse
import com.example.data.service.UserService
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
class RegisteredTracksPagingSource(
    private val userService: UserService,
    private val userId: Long,
    private val onTotalCountFetched: (Int) -> Unit
): PagingSource<String, RegisteredTrackResponse>() {
    override fun getRefreshKey(state: PagingState<String, RegisteredTrackResponse>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RegisteredTrackResponse> {
        return try {
            val currentCursor = params.key

            val response = userService.getRegisteredTracks(
                userId = userId,
                page = currentCursor,
                size = null
            )

            val data = response.data ?: throw Exception("data is null")
            if (params.key == null) {
                onTotalCountFetched(data.totalCount)
            }
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