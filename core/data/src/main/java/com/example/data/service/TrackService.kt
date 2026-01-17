package com.example.data.service

import com.example.data.constant.ApiConstants.API
import com.example.data.constant.ApiConstants.TRACKS
import com.example.data.constant.ApiConstants.VERSIONS
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TrackPreviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackService {
    @GET("$API/$VERSIONS/$TRACKS/preview/{trackId}")
    suspend fun getTrackPreview(
        @Path("trackId") trackId: String,
        @Query("storefront") storefront: String? = null,
    ): BaseResponse<TrackPreviewResponse>
}
