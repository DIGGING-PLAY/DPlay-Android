package com.example.common.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TodayQuestion(
    val questionId: Int,
    val title: String,
    private val date: String,
) {
    val dateText: String = date.toDiscoveryTitleSafe()
}

private fun String.toDiscoveryTitleSafe(): String =
    runCatching {
        val date = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        "${date.monthValue}월 ${date.dayOfMonth}일의 발견"
    }.getOrElse {
        "알 수 없는 날짜"
    }
