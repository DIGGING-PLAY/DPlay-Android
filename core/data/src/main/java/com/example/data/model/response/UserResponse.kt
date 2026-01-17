package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("user")
    val user: UserResponse,
    @SerialName("isHost")
    val isHost: Boolean,
    @SerialName("pushOn")
    val pushOn: Boolean,
    @SerialName("postTotalCount")
    val postTotalCount: Long,
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
