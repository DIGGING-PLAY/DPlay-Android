package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLikeResponse(
    @SerialName("likeCount")
    val likeCount: Int,
)
