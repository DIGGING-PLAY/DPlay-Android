package com.example.domain.model

data class HomeScreenData(
    val todayQuestion: DailyQuestion,
    val hasPosted: Boolean,
    val locked: Boolean,
    val totalCount: Int,
    val todayPosts: List<FeedItem>,
)