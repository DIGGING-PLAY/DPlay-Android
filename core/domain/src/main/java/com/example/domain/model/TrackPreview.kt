package com.example.domain.model

data class TrackPreview(
    val sessionId: String,
    val trackId: String,
    val streamUrl: String,
    val expiresAt: String? = null,
)
