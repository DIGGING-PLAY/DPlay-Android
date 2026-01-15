package com.example.data.model.response

import com.example.domain.model.Track
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val isrc: String? = null
){
    fun toDomain() = Track(
        trackId = trackId,
        songTitle = songTitle,
        artistName = artistName,
        coverImg = coverImg,
        isrc = isrc ?: ""
    )
}