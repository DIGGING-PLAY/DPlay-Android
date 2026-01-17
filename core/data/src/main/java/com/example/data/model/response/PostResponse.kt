package com.example.data.model.response

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class PostResponse(
    @SerialName("postId")
    val postId: Long,
)
