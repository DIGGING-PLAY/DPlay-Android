package com.example.common.constant

object Regex {
    // 문자 허용 범위: 한글 완성형(가–힣), 영문 A–Z/a–z, 숫자 0–9 (특수문자/이모지/초성·자모 단독 불가)
    val NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]+\$".toRegex()
}
