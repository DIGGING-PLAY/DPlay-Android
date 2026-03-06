package com.example.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class PostDetail(
    val postId: Long,
    val isHost: Boolean,
    val isScrapped: Boolean,
    val content: String,
    private val date: String,
    val track: Track,
    val writer: Writer,
    val like: Like,
) {
    val displayDate: String
        get() = runCatching {
            val parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
            "${parsedDate.monthValue}월 ${parsedDate.dayOfMonth}일"
        }.getOrElse { "알 수 없는 날짜" }
}