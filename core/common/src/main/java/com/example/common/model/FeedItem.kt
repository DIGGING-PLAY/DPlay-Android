package com.example.common.model

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

data class Track(
    val trackId: String,
    val songTitle: String,
    val coverImg: String,
    val artistNames: List<String>,
)

data class Writer(
    val userId: Long,
    val nickname: String,
    val profileImg: String,
)

data class Like(
    val isLiked: Boolean,
    val count: Int,
)
