package com.example.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dplay.designsystem.R
import com.example.designsystem.component.DplayLeftIconTopAppBar
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingRoute(
    onboardingNavigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when(sideEffect){
                OnboardingContract.OnboardingSideEffect.NavigateToBack -> {
                    onboardingNavigator.navigateToBack()
                }
                OnboardingContract.OnboardingSideEffect.NavigateToPermission -> {
                    onboardingNavigator.navigateTo(OnboardingGraph.Permission)
                }
                else -> {}
            }
        }
    }

    OnboardingScreen(
        onStartButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnStartButtonClick)
        }
    )
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onStartButtonClick: () -> Unit = {}
) {

    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(bottom = 16.dp),
    ){
        DplayLeftIconTopAppBar {  }

        Spacer(modifier = Modifier.height(40.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Top
        ) { page ->
            when(page){
                0 -> FirstOnboardingPage()
                1 -> SecondOnboardingPage()
                2 -> ThirdOnboardingPage()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        DotIndicator(
            currentPage = pagerState.currentPage,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(54.dp))

        DPlayLargePinkButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = { onStartButtonClick() },
            label = stringResource(R.string.start_button_label),
        )
    }
}

@Composable
private fun FirstOnboardingPage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(com.dplay.onboarding.R.string.first_onboarding_page_main_text),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(com.dplay.onboarding.R.string.first_onboarding_page_sub_text),
            style = DPlayTheme.typography.bodyMed16,
            color = DPlayTheme.colors.gray400,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SecondOnboardingPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(com.dplay.onboarding.R.string.second_onboarding_page_main_text),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(com.dplay.onboarding.R.string.second_onboarding_page_sub_text),
            style = DPlayTheme.typography.bodyMed16,
            color = DPlayTheme.colors.gray400,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ThirdOnboardingPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(com.dplay.onboarding.R.string.third_onboarding_page_main_text),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(com.dplay.onboarding.R.string.third_onboarding_page_sub_text),
            style = DPlayTheme.typography.bodyMed16,
            color = DPlayTheme.colors.gray400,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(id = R.drawable.img_onboarding_3),
            contentDescription = null,
        )
    }
}

@Composable
private fun DotIndicator(
    currentPage: Int,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) { iteration ->
            val color = if (currentPage == iteration) DPlayTheme.colors.dplayBlack else DPlayTheme.colors.gray200

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    DPlayTheme{
        OnboardingScreen()
    }
}