package com.example.setting

enum class SettingMenuType(
    val title: String,
) {
    PUSH_NOTIFICATION("푸시 알림"),
    ANNOUNCEMENT("공지사항"),
    INQUIRY("문의/제안하기"),
    TERMS("서비스 이용 약관"),
    PRIVACY("개인정보 처리방침"),
    VERSION("앱 버전"),
    LOGOUT("로그아웃"),
    WITHDRAW("회원탈퇴");
}