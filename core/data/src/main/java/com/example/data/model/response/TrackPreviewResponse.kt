package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackPreviewResponse(
    @SerialName("sessionId")
    val sessionId: String,
    @SerialName("trackId")
    val trackId: String,
    @SerialName("streamUrl")
    val streamUrl: String,
    @SerialName("expiresAt")
    val expiresAt: String? = null,
)
