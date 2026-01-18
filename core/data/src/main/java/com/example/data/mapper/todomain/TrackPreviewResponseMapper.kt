package com.example.data.mapper.todomain

import com.example.data.model.response.TrackPreviewResponse
import com.example.domain.model.TrackPreview

fun TrackPreviewResponse.toDomain(): TrackPreview =
    TrackPreview(
        sessionId = this.sessionId,
        trackId = this.trackId,
        streamUrl = this.streamUrl,
        expiresAt = this.expiresAt,
    )
