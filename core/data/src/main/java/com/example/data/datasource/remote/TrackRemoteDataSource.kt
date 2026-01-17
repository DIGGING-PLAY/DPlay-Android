package com.example.data.datasource.remote

import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TrackPreviewResponse
import com.example.data.service.TrackService
import javax.inject.Inject

class TrackRemoteDataSource
    @Inject
    constructor(
        private val trackService: TrackService,
    ) {
        suspend fun getTrackPreview(
            trackId: String,
            storefront: String? = null,
        ): BaseResponse<TrackPreviewResponse> = trackService.getTrackPreview(trackId = trackId, storefront = storefront)
    }
