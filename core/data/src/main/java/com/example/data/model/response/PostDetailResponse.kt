package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailResponse(
    @SerialName("postId")
    val postId: Long,
    @SerialName("isHost")
    val isHost: Boolean,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
    @SerialName("content")
    val content: String,
    @SerialName("track")
    val track: TrackResponse,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("like")
    val like: LikeResponse,
)

@Serializable
data class LikeResponse(
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("count")
    val count: Int,
)
