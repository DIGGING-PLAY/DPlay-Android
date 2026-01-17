package com.example.domain.model

data class Track(
    val trackId: String,
    val songTitle: String,
    val artistName: String,
    val coverImg: String,
    val isrc: String,
)