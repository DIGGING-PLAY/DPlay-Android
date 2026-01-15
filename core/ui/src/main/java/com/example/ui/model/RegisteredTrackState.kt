package com.example.ui.model

import com.example.domain.model.RegisteredTrack

data class RegisteredTrackState(
    val postId: Long,
    val comment: String,
    val track: TrackState,
)

fun RegisteredTrack.toUiState() =
    RegisteredTrackState(
        postId = postId,
        comment = comment,
        track = track.toUiState(),
    )
