package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String?,
)
