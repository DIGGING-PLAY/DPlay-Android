package com.example.common.type

import com.example.common.constant.Url

enum class TermType(
    val isMandatory: Boolean,
    val url: String,
) {
    TERMS_OF_SERVICE(isMandatory = true, url = Url.TERMS_OF_SERVICE), // 서비스 이용약관 (필수)
    PRIVACY_POLICY(isMandatory = true, url = Url.PRIVACY_POLICY), // 개인정보 처리방침 (필수)
    ;

    companion object {
        val mandatoryTerms = entries.filter { it.isMandatory }.toSet()
    }
}
