package com.example.onboarding

import com.example.navigation.Navigator
import com.example.navigation.OnboardingProfile
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<OnboardingContract.OnboardingState, OnboardingContract.OnboardingIntent, OnboardingContract.OnboardingSideEffect>(
    OnboardingContract.OnboardingState(),
) {
    override fun handleIntent(intent: OnboardingContract.OnboardingIntent) {
        when (intent) {
            is OnboardingContract.OnboardingIntent.OnToggleTerm -> {
                updateState {
                    val newAgreedTerms = if (agreedTerms.contains(intent.term)) {
                        agreedTerms - intent.term
                    } else {
                        agreedTerms + intent.term
                    }
                    copy(agreedTerms = newAgreedTerms)
                }
            }

            OnboardingContract.OnboardingIntent.OnToggleAllTerms -> {
                updateState {
                    val newAgreedTerms = if (currentState.isAllTermsAgreed) {
                        emptySet()
                    } else {
                        TermType.entries.toSet()
                    }
                    copy(agreedTerms = newAgreedTerms)
                }
            }

            OnboardingContract.OnboardingIntent.OnNextButtonClick -> {
                navigator.goTo(OnboardingProfile)
            }
        }
    }
}