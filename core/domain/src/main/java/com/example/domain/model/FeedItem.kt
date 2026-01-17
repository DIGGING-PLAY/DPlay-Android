package com.example.domain.model

data class FeedItem(
    val postId: Long,
    val isScrapped: Boolean,
    val content: String,
    val badges: Badges,
    val track: Track,
    val writer: Writer,
    val like: Like,
)

data class Badges(
    val isEditorPick: Boolean,
    val isPopular: Boolean,
    val isNew: Boolean,
)