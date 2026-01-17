package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.TRACKS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.SearchTrackResponse
import com.example.data.model.response.TrackPreviewResponse
import com.example.data.model.response.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackService {
    @GET("$API/$VERSIONS/$TRACKS")
    suspend fun searchTracks(
        @Query("query") query: String,
        @Query("limit") limit: Int?,
        @Query("storefront") storefront: String?,
        @Query("cursor") cursor: String?,
    ): BaseResponse<SearchTrackResponse>

    @GET("$API/$VERSIONS/$TRACKS/{trackId}")
    suspend fun getTrack(
        @Path("trackId") trackId: String,
        @Query("storefront") storefront: String?,
    ): BaseResponse<TrackResponse>

    @GET("$API/$VERSIONS/$TRACKS/preview/{trackId}")
    suspend fun getTrackPreview(
        @Path("trackId") trackId: String,
        @Query("storefront") storefront: String? = null,
    ): BaseResponse<TrackPreviewResponse>
}

