package com.example.data.model.response

import com.example.domain.model.RegisteredTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisteredTracksResponse(
    @SerialName("visibleLimit")
    val visibleLimit: Int,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<RegisteredTrackResponse>,
    @SerialName("isHost")
    val isHost: Boolean
)

@Serializable
data class RegisteredTrackResponse(
    @SerialName("postId")
    val postId: Long,
    @SerialName("track")
    val track: TrackResponse,
    @SerialName("comment")
    val content: String,
){
    fun toDomain() = RegisteredTrack(
        postId = postId,
        track = track.toDomain(),
        comment = content
    )
}
