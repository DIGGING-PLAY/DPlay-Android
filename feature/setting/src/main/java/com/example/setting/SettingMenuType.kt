package com.example.setting

import androidx.annotation.StringRes
import com.dplay.setting.R
import com.example.common.constant.Url

enum class SettingMenuType(
    @StringRes val titleResId: Int,
    val url: String? = null,
) {
    PUSH_NOTIFICATION(R.string.setting_push_notification),
    ANNOUNCEMENT(R.string.setting_announcement, Url.ANNOUNCEMENT),
    INQUIRY(R.string.setting_inquiry, Url.INQUIRY),
    TERMS(R.string.setting_terms, Url.TERMS_OF_SERVICE),
    PRIVACY(R.string.setting_privacy, Url.PRIVACY_POLICY),
    VERSION(R.string.setting_version),
    LOGOUT(R.string.setting_logout),
    WITHDRAW(R.string.setting_withdraw),
}
