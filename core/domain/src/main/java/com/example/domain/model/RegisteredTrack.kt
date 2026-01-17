package com.example.domain.model

data class RegisteredTrack(
    val postId: Long,
    val track: Track,
    val comment: String,
)
