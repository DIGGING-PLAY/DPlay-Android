package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailResponse(
    @SerialName("questionId")
    val questionId: Long,
    @SerialName("date")
    val date: String,
    @SerialName("title")
    val title: String,
    @SerialName("hasPosted")
    val hasPosted: Boolean,
    @SerialName("locked")
    val locked: Boolean,
    @SerialName("visibleLimit")
    val visibleLimit: Int,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<PostItemResponse>,
)

@Serializable
data class PostItemResponse(
    @SerialName("postId")
    val postId: Long,
    @SerialName("isEditorPick")
    val isEditorPick: Boolean,
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

// @Serializable
// data class PostDetailResponse(
//    @SerialName("postId")
//    val postId: Long,
//    @SerialName("isHost")
//    val isHost: Boolean,
//    @SerialName("isScrapped")
//    val isScrapped: Boolean,
//    @SerialName("content")
//    val content: String,
//    @SerialName("track")
//    val track: TrackResponse,
//    @SerialName("user")
//    val user: UserResponse,
//    @SerialName("like")
//    val like: LikeResponse,
// )
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
//    @SerialName("isrc")
//    val isrc: String,
)

@Serializable
data class UserResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String?,
)

@Serializable
data class LikeResponse(
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("count")
    val count: Int,
)
