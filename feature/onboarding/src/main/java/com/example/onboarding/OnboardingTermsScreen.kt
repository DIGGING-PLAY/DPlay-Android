package com.example.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.common.type.TermType
import com.example.designsystem.component.DPlayCheckArrow
import com.example.designsystem.component.DPlayCheckBox
import com.example.designsystem.component.DplayLeftIconTopAppBar
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingTermsRoute(
    onboardingNavigator: Navigator,
    globalNavigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OnboardingContract.OnboardingSideEffect.NavigateToBack -> {
                    globalNavigator.navigateToBack()
                }
                OnboardingContract.OnboardingSideEffect.NavigateToProfile -> {
                    onboardingNavigator.navigateTo(OnboardingGraph.Profile)
                }
                else -> {}
            }
        }
    }

    OnboardingTermsScreen(
        state = state,
        onToggleTerm = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnToggleTerm(it))
        },
        onToggleAllTerms = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnToggleAllTerms)
        },
        onNextButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnTermsScreenNextButtonClick)
        },
        onBackButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnBackButtonClick)
        },
    )
}

@Composable
fun OnboardingTermsScreen(
    state: OnboardingContract.OnboardingState,
    modifier: Modifier = Modifier,
    onToggleTerm: (TermType) -> Unit = {},
    onToggleAllTerms: () -> Unit = {},
    onNextButtonClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite),
    ) {
        DplayLeftIconTopAppBar { onBackButtonClick() }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp, bottom = 16.dp),
        ) {
            Text(
                text = stringResource(com.dplay.onboarding.R.string.terms_screen_title),
                style = DPlayTheme.typography.titleBold24,
                color = DPlayTheme.colors.dplayBlack,
            )

            Spacer(modifier = Modifier.height(40.dp))

            DPlayCheckBox(
                text = stringResource(R.string.agree_all_terms),
                isChecked = state.isAllTermsAgreed,
                onClick = onToggleAllTerms,
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheckArrow(
                text = stringResource(id = R.string.terms_service_required),
                isChecked = state.agreedTerms.contains(TermType.TERMS_OF_SERVICE),
                onArrowClick = {},
                onCheckBoxClick = { onToggleTerm(TermType.TERMS_OF_SERVICE) },
                modifier = Modifier.fillMaxWidth(),
            )

            DPlayCheckArrow(
                text = stringResource(id = R.string.privacy_policy_required),
                isChecked = state.agreedTerms.contains(TermType.PRIVACY_POLICY),
                onArrowClick = {},
                onCheckBoxClick = { onToggleTerm(TermType.PRIVACY_POLICY) },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.weight(1f))

            DPlayLargePinkButton(
                onClick = onNextButtonClick,
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.next_button_label),
                enabled = state.isTermsScreenNextButtonEnabled,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingTermsScreenPreview() {
    DPlayTheme {
        OnboardingTermsScreen(
            state = OnboardingContract.OnboardingState(),
        )
    }
}
