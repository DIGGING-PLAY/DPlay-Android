package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayPostsResponse(
    @SerialName("questionId") val questionId: Long,
    @SerialName("date") val date: String,
    @SerialName("title") val title: String,
    @SerialName("hasPosted") val hasPosted: Boolean,
    @SerialName("locked") val locked: Boolean,
    @SerialName("totalCount") val totalCount: Int,
    @SerialName("items") val items: List<TodayPostItemResponse>,
)

@Serializable
data class TodayPostItemResponse(
    @SerialName("postId") val postId: Long,
    @SerialName("isScrapped") val isScrapped: Boolean,
    @SerialName("content") val content: String,
    @SerialName("badge") val badge: String?,
    @SerialName("track") val track: TodayPostTrackResponse,
    @SerialName("user") val user: UserResponse,
    @SerialName("like") val like: LikeResponse,
)

@Serializable
data class TodayPostTrackResponse(
    @SerialName("trackId") val trackId: String,
    @SerialName("songTitle") val songTitle: String,
    @SerialName("coverImg") val coverImg: String,
    @SerialName("artistName") val artistName: String,
)
