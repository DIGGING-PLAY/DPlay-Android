package com.example.data.model.response

import com.example.domain.model.Track
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class SearchTrackResponse(
    @SerialName("query")
    val query: String,
    @SerialName("storefront")
    val storefront: String,
    @SerialName("limit")
    val limit: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<TrackResponse>
)

@Serializable
data class TrackResponse(
    @SerialName("trackId")
    val trackId: String,
    @SerialName("songTitle")
    val songTitle: String,
    @SerialName("artistName")
    val artistName: String,
    @SerialName("coverImg")
    val coverImg: String,
    @SerialName("isrc")
    val isrc: String
){
    fun toDomain() = Track(
        trackId = trackId,
        songTitle = songTitle,
        artistName = artistName,
        coverImg = coverImg,
        isrc = isrc
    )
}