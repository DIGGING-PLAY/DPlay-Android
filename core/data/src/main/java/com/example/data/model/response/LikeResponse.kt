package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("count")
    val count: Int,
)
