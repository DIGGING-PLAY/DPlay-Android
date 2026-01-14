package com.example.ui.model

import com.example.domain.model.Track

data class Music(
    val trackId: String,
    val musicTitle: String,
    val artistName: String,
    val thumbnailUrl: String?,
)

fun Track.toUiState(): Music = Music(
    trackId = trackId,
    musicTitle = songTitle,
    artistName = artistName,
    thumbnailUrl = coverImg
)
