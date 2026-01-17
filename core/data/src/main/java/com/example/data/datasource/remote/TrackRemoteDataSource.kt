package com.example.data.datasource.remote

import com.example.data.model.response.BaseResponse
import com.example.data.model.response.TrackPreviewResponse
import com.example.data.model.response.TrackResponse
import com.example.data.service.TrackService
import timber.log.Timber
import javax.inject.Inject

class TrackRemoteDataSource
    @Inject
    constructor(
        private val trackService: TrackService,
    ) {
        suspend fun getTrack(
            trackId: String,
        ): TrackResponse {
            try {
                val response =
                    trackService.getTrack(
                        trackId = trackId,
                        storefront = null,
                    )

                return response.data ?: throw Exception("Data is null")
            } catch (e: Exception) {
                Timber.e(e, "getTrack 실패")
                throw e
            }
        }

        suspend fun getTrackPreview(
            trackId: String,
            storefront: String? = null,
        ): BaseResponse<TrackPreviewResponse> = trackService.getTrackPreview(trackId = trackId, storefront = storefront)
    }
