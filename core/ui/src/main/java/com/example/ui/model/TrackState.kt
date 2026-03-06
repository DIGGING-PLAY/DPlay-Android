package com.example.ui.model

import com.example.domain.model.Track

data class TrackState(
    val trackId: String,
    val musicTitle: String,
    val artistName: String,
    val thumbnailUrl: String,
    val isrc: String,
)

fun Track.toUiState(): TrackState =
    TrackState(
        trackId = trackId,
        musicTitle = songTitle,
        artistName = artistName,
        thumbnailUrl = coverImg,
        isrc = isrc,
    )

fun TrackState.toDomain(): Track =
    Track(
        trackId = trackId,
        songTitle = musicTitle,
        artistName = artistName,
        coverImg = thumbnailUrl,
        isrc = isrc,
    )
