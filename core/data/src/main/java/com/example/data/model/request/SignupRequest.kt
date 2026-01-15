package com.example.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    @SerialName("platform")
    val platform: String,
    @SerialName("nickname")
    val nickname: String,
)
