package com.example.setting

import androidx.annotation.StringRes
import com.dplay.setting.R

enum class SettingMenuType(
    @StringRes val titleResId: Int,
) {
    PUSH_NOTIFICATION(R.string.setting_push_notification),
    ANNOUNCEMENT(R.string.setting_announcement),
    INQUIRY(R.string.setting_inquiry),
    TERMS(R.string.setting_terms),
    PRIVACY(R.string.setting_privacy),
    VERSION(R.string.setting_version),
    LOGOUT(R.string.setting_logout),
    WITHDRAW(R.string.setting_withdraw);
}