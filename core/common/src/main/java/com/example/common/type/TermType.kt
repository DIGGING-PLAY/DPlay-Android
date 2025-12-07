package com.example.common.type

enum class TermType(val isMandatory: Boolean) {
    TERMS_OF_SERVICE(isMandatory = true), // 서비스 이용약관 (필수)
    PRIVACY_POLICY(isMandatory = true);   // 개인정보 처리방침 (필수)

    companion object {
        val mandatoryTerms = entries.filter { it.isMandatory }.toSet()
    }
}