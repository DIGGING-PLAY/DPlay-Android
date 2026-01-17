package com.example.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayToggle
import com.example.designsystem.component.button.DPlayUnderlineTextButton
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.Login
import com.example.navigation.Navigator
import com.example.ui.controller.LocalModalController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val modalController = LocalModalController.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                SettingContract.SettingSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                SettingContract.SettingSideEffect.NavigateToLogin -> {
                    navigator.clearAndNavigateTo(Login)
                }
                is SettingContract.SettingSideEffect.NavigateToWeb -> {}
                SettingContract.SettingSideEffect.ShowLogoutWarningDialog -> {
                    modalController.showWarningModal(
                        mainText = context.getString(com.dplay.setting.R.string.logout_warning_mainText),
                        subText = null,
                        onLeftButtonClick = { modalController.hideModal() },
                        onRightButtonClick = {
                            modalController.hideModal()
                            viewModel.handleIntent(SettingContract.SettingIntent.OnLogoutConfirm)
                        },
                        onDismiss = { modalController.hideModal() },
                        leftButtonLabel = context.getString(com.dplay.setting.R.string.logout_warning_left_button_label),
                        rightButtonLabel = context.getString(com.dplay.setting.R.string.logout_warning_right_button_label),
                    )
                }
                SettingContract.SettingSideEffect.ShowWithdrawWarningDialog -> {
                    modalController.showWarningModal(
                        mainText = context.getString(com.dplay.setting.R.string.withdraw_warning_main_text),
                        subText = context.getString(com.dplay.setting.R.string.withdraw_warning_sub_text),
                        onLeftButtonClick = {
                            modalController.hideModal()
                            viewModel.handleIntent(SettingContract.SettingIntent.OnWithdrawConfirm)
                        },
                        onRightButtonClick = { modalController.hideModal() },
                        onDismiss = { modalController.hideModal() },
                        leftButtonLabel = context.getString(com.dplay.setting.R.string.withdraw_warning_left_button_label),
                        rightButtonLabel = context.getString(com.dplay.setting.R.string.withdraw_warning_right_button_label),
                    )
                }
                is SettingContract.SettingSideEffect.OpenWebView -> {
                    uriHandler.openUri(sideEffect.url)
                }
            }
        }
    }

    SettingScreen(
        state = state,
        onBackIconClick = {
            viewModel.handleIntent(SettingContract.SettingIntent.OnBackIconClick)
        },
        onMenuClick = {
            viewModel.handleIntent(SettingContract.SettingIntent.OnMenuClick(it))
        },
    )
}

@Composable
fun SettingScreen(
    state: SettingContract.SettingState,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onMenuClick: (SettingMenuType) -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
                .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DplayLeftIconTitleTopAppBar(
            title = stringResource(com.dplay.setting.R.string.setting_screen_title),
        ) {
            onBackIconClick()
        }

        SettingMenuType.entries.forEach { type ->
            val titleColor = if (type == SettingMenuType.LOGOUT) DPlayTheme.colors.alertRed else DPlayTheme.colors.gray600

            if (type != SettingMenuType.WITHDRAW) {
                SettingActionRow(
                    stringResource(id = type.titleResId),
                    titleColor = titleColor,
                    onClick = { onMenuClick(type) },
                ) {
                    when (type) {
                        SettingMenuType.PUSH_NOTIFICATION -> {
                            DPlayToggle(
                                isChecked = state.isPushNotificationEnabled,
                                onClick = {
                                    onMenuClick(type)
                                },
                            )
                        }
                        SettingMenuType.VERSION -> {
                            Text(
                                text = state.appVersion,
                                style = DPlayTheme.typography.bodyMed14,
                                color = DPlayTheme.colors.gray400,
                            )
                        }
                        SettingMenuType.LOGOUT -> {}
                        else -> {
                            DplayBaseIcon(
                                iconRes = R.drawable.ic_arrow_right_16,
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))

                DPlayUnderlineTextButton(
                    text = stringResource(id = type.titleResId),
                    onClick = { onMenuClick(type) },
                )
            }

            if (type == SettingMenuType.INQUIRY) {
                HorizontalDivider(
                    thickness = 8.dp,
                    color = DPlayTheme.colors.gray100,
                )
            }
        }
    }
}

@Composable
private fun SettingActionRow(
    actionName: String,
    modifier: Modifier = Modifier,
    titleColor: Color = DPlayTheme.colors.gray600,
    onClick: () -> Unit = {},
    trailingContent: @Composable () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .noRippleClickable { onClick() }
                .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = actionName,
            color = titleColor,
            style = DPlayTheme.typography.bodySemi16,
        )

        Spacer(modifier = Modifier.weight(1f))

        trailingContent()
    }
}

@Preview
@Composable
private fun SettingScreenPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        SettingScreen(
            state = SettingContract.SettingState(),
        )
    }
}
