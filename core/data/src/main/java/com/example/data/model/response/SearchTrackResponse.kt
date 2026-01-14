package com.example.data.model.response

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class SearchTrackResponse(
    val query: String,
    val storefront: String,
    val limit: Int,
    val nextCursor: String?,
    val items: List<Track>
)

@Serializable
data class Track(
    val trackId: String,
    val songTitle: String,
    val artistName: String,
    val coverImg: String,
    val isrc: String
)