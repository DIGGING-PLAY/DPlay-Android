package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.TRACKS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.SearchTrackResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Query

@OptIn(InternalSerializationApi::class)
interface TrackService {
    @GET("$API/$VERSIONS/$TRACKS")
    suspend fun searchTracks(
        @Query("query") query: String,
        @Query("limit") limit: Int?,
        @Query("storefront") storefront: String?,
        @Query("cursor") cursor: String?,
    ): BaseResponse<SearchTrackResponse>
}