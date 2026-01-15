package com.example.ui.model

import com.example.domain.model.ScrappedTrack

data class ScrappedTrackState(
    val postId: Long,
    val track: TrackState,
)

fun ScrappedTrack.toUiState() =
    ScrappedTrackState(
        postId = postId,
        track = track.toUiState()
    )