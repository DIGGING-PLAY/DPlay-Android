package com.example.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PatchProfileRequest(
    val nickname: String?
)
