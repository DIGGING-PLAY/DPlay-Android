package com.example.domain.model

data class FeedItem(
    val postId: Long,
    val isScrapped: Boolean,
    val content: String,
    val badge: BADGE?,
    val track: Track,
    val writer: Writer,
    val like: Like,
)

enum class BADGE {
    EDITOR,
    BEST,
    NEW,
}