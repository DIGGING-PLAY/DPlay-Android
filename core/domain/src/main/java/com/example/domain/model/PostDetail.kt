package com.example.domain.model

data class PostDetail(
    val postId: Long,
    val date: String,
    val isHost: Boolean,
    val isScrapped: Boolean,
    val content: String,
    val track: Track,
    val writer: Writer,
    val like: Like,
)