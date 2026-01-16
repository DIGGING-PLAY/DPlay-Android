package com.example.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DailyQuestion(
    val questionId: Long,
    val title: String,
    private val date: String,
) {
    val homeTitleDateText: String = date.toDiscoveryTitleSafe()

    val recordDayText: String = date

    val recordMMDD: String = date.toMMDDText()
}

private fun String.toDiscoveryTitleSafe(): String =
    runCatching {
        val date = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        "${date.monthValue}월 ${date.dayOfMonth}일의 발견"
    }.getOrElse {
        "알 수 없는 날짜"
    }

private fun String.toRecordListDayTextSafe(): Int =
    runCatching {
        val day = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        day.dayOfMonth
    }.getOrElse {
        -1
    }

private fun String.toMMDDText(): String =
    runCatching {
        val date = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        "${date.monthValue}월 ${date.dayOfMonth}일"
    }.getOrElse {
        "알 수 없는 날짜"
    }
