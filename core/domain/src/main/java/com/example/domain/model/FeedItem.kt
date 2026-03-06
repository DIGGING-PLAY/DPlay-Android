package com.example.domain.model


data class FeedItem(
    val postId: Long,
    val isScrapped: Boolean,
    val content: String,
    val badge: Badge?,
    val track: Track,
    val writer: Writer,
    val like: Like,
)

enum class Badge {
    EDITOR,
    BEST,
    NEW,
}