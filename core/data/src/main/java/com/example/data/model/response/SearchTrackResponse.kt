package com.example.data.model.response

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class SearchTrackResponse(
    @SerialName("query")
    val query: String,
    @SerialName("storefront")
    val storefront: String,
    @SerialName("limit")
    val limit: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<TrackResponse>,
)
