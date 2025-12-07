package com.example.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dplay.designsystem.R
import com.example.designsystem.component.button.DPlayKakaoLoginButton
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Home
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    navigator: Navigator,
    viewModel: LoginViewModel = hiltViewModel(),
){
    LaunchedEffect(Unit){
        viewModel.sideEffect.collectLatest { sideEffect ->
            when(sideEffect){
                LoginContract.LoginSideEffect.NavigateToOnboarding -> {
                    navigator.goTo(OnboardingGraph)
                }
                LoginContract.LoginSideEffect.NavigateToHome -> {
                    navigator.backStack.clear()
                    navigator.goTo(Home)
                }
            }
        }
    }
    LoginScreen(
        onKaKaoLogin = {
            viewModel.handleIntent(LoginContract.LoginIntent.OnKakaoLogin)
        }
    )
}

@Composable
fun LoginScreen(
    onKaKaoLogin: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(top = 200.dp, bottom = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(com.dplay.login.R.string.login_title),
            style = DPlayTheme.typography.bodyBold16,
        )

        Image(
            painter = painterResource(R.drawable.img_wordmark_pink),
            contentDescription = null,
            modifier = Modifier.size(width = 200.dp, height = 60.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        DPlayKakaoLoginButton(
            onClick = onKaKaoLogin,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(){
    DPlayTheme {
        LoginScreen()
    }
}