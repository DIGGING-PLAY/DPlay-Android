package com.example.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dplay.designsystem.R
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.Home
import com.example.navigation.Navigator
import com.example.onboarding.OnboardingContract.OnboardingIntent.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingPermissionRoute(
    onboardingNavigator: Navigator,
    globalNavigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            viewModel.handleIntent(
                OnboardingContract.OnboardingIntent.OnNotificationPermissionResult(isGranted),
            )
        }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                OnboardingContract.OnboardingSideEffect.ShowPermissionDialog -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hasPermission =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS,
                            ) == PackageManager.PERMISSION_GRANTED

                        if (!hasPermission) {
                            notificationPermissionLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS,
                            )
                        } else {
                            viewModel.handleIntent(
                                OnNotificationPermissionResult(
                                    isGranted = true,
                                ),
                            )
                        }
                    } else {
                        viewModel.handleIntent(
                            OnNotificationPermissionResult(
                                isGranted = true,
                            ),
                        )
                    }
                }

                OnboardingContract.OnboardingSideEffect.NavigateToBack -> {
                    onboardingNavigator.navigateToBack()
                }

                OnboardingContract.OnboardingSideEffect.NavigateToHome -> {
                    globalNavigator.clearAndNavigateTo(Home)
                }

                else -> {}
            }
        }
    }
    OnboardingPermissionScreen(
        onPermissionConfirmButtonClick = {
            viewModel.handleIntent(OnboardingContract.OnboardingIntent.OnPermissionConfirmButtonClick)
        },
    )
}

@Composable
fun OnboardingPermissionScreen(
    modifier: Modifier = Modifier,
    onPermissionConfirmButtonClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
                .padding(horizontal = 16.dp)
                .padding(top = 110.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_wordmark_pink),
            contentDescription = null,
            modifier = Modifier.size(width = 100.dp, height = 30.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(com.dplay.onboarding.R.string.permission_screen_title),
            style = DPlayTheme.typography.titleBold18,
            color = DPlayTheme.colors.dplayBlack,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PermissionBox()

        Spacer(modifier = Modifier.weight(1f))

        DPlayLargePinkButton(
            onClick = { onPermissionConfirmButtonClick() },
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.confirm_button_label),
        )
    }
}

@Composable
private fun PermissionBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable(
                    onClick = onClick,
                ).border(
                    width = 1.dp,
                    color = DPlayTheme.colors.gray200,
                    shape = RoundedCornerShape(16.dp),
                ).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .background(
                        color = DPlayTheme.colors.gray100,
                        shape = CircleShape,
                    ).padding(10.dp),
        ) {
            DplayBaseIcon(
                iconRes = R.drawable.ic_alert_24,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = stringResource(com.dplay.onboarding.R.string.permission_box_title),
                style = DPlayTheme.typography.bodyBold14,
            )

            Text(
                text = stringResource(com.dplay.onboarding.R.string.permission_box_sub_text),
                style = DPlayTheme.typography.bodyMed14,
                color = DPlayTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingPermissionScreenPreview() {
    OnboardingPermissionScreen()
}
