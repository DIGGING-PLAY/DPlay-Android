package com.example.data.datasource.remote

import com.example.data.model.response.TrackResponse
import com.example.data.service.TrackService
import kotlinx.serialization.InternalSerializationApi
import timber.log.Timber
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
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
    }
