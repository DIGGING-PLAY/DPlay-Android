package com.example.data.model.response

import com.example.domain.model.ScrappedTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrappedTracksResponse(
    @SerialName("visibleLimit")
    val visibleLimit: Int,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<ScrappedTrackResponse>,
)

@Serializable
data class ScrappedTrackResponse(
    @SerialName("postId")
    val postId: Long,
    @SerialName("track")
    val track: TrackResponse,
){
    fun toDomain() = ScrappedTrack(
        postId = postId,
        track = track.toDomain(),
    )
}