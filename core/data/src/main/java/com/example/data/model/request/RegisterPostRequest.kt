package com.example.data.model.request

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class RegisterPostRequest(
    @SerialName("trackId")
    val trackId: String,
    @SerialName("songTitle")
    val songTitle: String,
    @SerialName("artistName")
    val artistName: String,
    @SerialName("coverImg")
    val coverImg: String,
    @SerialName("isrc")
    val isrc: String,
    @SerialName("content")
    val content: String,
)
