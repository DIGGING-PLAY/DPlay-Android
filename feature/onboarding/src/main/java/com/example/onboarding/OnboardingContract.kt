package com.example.onboarding

import com.example.ui.base.BaseContract

class OnboardingContract {
    data class OnboardingState(
        val agreedTerms: Set<TermType> = emptySet(),
    ) : BaseContract.State {

        val isNextButtonEnabled: Boolean
            get() = agreedTerms.containsAll(TermType.mandatoryTerms)

        val isAllTermsAgreed: Boolean
            get() = agreedTerms.size == TermType.entries.size
    }

    sealed interface OnboardingIntent : BaseContract.Intent {
        data class OnToggleTerm(val term: TermType) : OnboardingIntent

        data object OnToggleAllTerms : OnboardingIntent

        data object OnNextButtonClick : OnboardingIntent
    }

    sealed interface OnboardingSideEffect : BaseContract.SideEffect
}

enum class TermType(val isMandatory: Boolean) {
    TERMS_OF_SERVICE(isMandatory = true), // 서비스 이용약관 (필수)
    PRIVACY_POLICY(isMandatory = true);   // 개인정보 처리방침 (필수)

    companion object {
        val mandatoryTerms = entries.filter { it.isMandatory }.toSet()
    }
}