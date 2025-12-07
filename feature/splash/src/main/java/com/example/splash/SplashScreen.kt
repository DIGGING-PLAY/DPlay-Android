package com.example.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Home
import com.example.navigation.Login
import com.example.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashRoute(
    navigator: Navigator,
    viewModel: SplashViewModel = hiltViewModel()
){
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                SplashContract.SplashSideEffect.NavigateToHome -> {
                    navigator.backStack.clear()
                    navigator.goTo(Home)
                }

                SplashContract.SplashSideEffect.NavigateToLogin -> {
                    navigator.backStack.clear()
                    navigator.goTo(Login)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(SplashContract.SplashIntent.OnSplashScreenStart)
    }

    SplashScreen()
}

@Composable
fun SplashScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayPink)
            .padding(top = 265.dp, start = 90.dp)
    ){
        Image(
            painter = painterResource(R.drawable.img_wordmark_white),
            contentDescription = null,
            modifier = Modifier.size(width = 200.dp, height = 60.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}