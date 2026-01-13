package com.example.data.model.request

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class LoginRequest(
    @SerialName("platform") val platform: String,
)
