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

enum class SettingMenuType(
    val title: String,
) {
    PUSH_NOTIFICATION("푸시 알림"),
    ANNOUNCEMENT("공지사항"),
    INQUIRY("문의/제안하기"),
    TERMS("서비스 이용 약관"),
    PRIVACY("개인정보 처리방침"),
    VERSION("앱 버전"),
    LOGOUT("로그아웃"),
    WITHDRAW("회원탈퇴");
}

@Composable
fun SettingRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val modalController = LocalModalController.current

    LaunchedEffect(Unit){
        viewModel.sideEffect.collectLatest{ sideEffect ->
            when(sideEffect){
                SettingContract.SettingSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                SettingContract.SettingSideEffect.NavigateToLogin -> {
                    navigator.clearAndNavigateTo(Login)
                }
                is SettingContract.SettingSideEffect.NavigateToWeb -> {}
                SettingContract.SettingSideEffect.ShowLogoutWarningDialog -> {
                    modalController.showWarningModal(
                        mainText = "로그아웃하시겠어요?",
                        subText = null,
                        onLeftButtonClick = { modalController.hideModal() },
                        onRightButtonClick = {
                            modalController.hideModal()
                            viewModel.handleIntent(SettingContract.SettingIntent.OnLogoutConfirm)
                        },
                        onDismiss = { modalController.hideModal() },
                        leftButtonLabel = "취소",
                        rightButtonLabel = "로그아웃"
                    )
                }
                SettingContract.SettingSideEffect.ShowWithdrawWarningDialog -> {
                    modalController.showWarningModal(
                        mainText = "정말 탈퇴하시겠어요?",
                        subText = "작성하신 글, 좋아요한 글, 저장한 글 등 모든 기록이 삭제되며 복구가 불가능해요.",
                        onLeftButtonClick = {
                            modalController.hideModal()
                            viewModel.handleIntent(SettingContract.SettingIntent.OnWithdrawConfirm)
                        },
                        onRightButtonClick = { modalController.hideModal() },
                        onDismiss = { modalController.hideModal() },
                        leftButtonLabel = "탈퇴하기",
                        rightButtonLabel = "머무르기",
                    )
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
        }
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
        modifier = modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DplayLeftIconTitleTopAppBar(
            title = "설정"
        ) {
            onBackIconClick()
        }

        SettingMenuType.entries.forEach { type ->
            val titleColor = if(type == SettingMenuType.LOGOUT) DPlayTheme.colors.alertRed else DPlayTheme.colors.gray600

            if(type != SettingMenuType.WITHDRAW){
                SettingActionRow(
                    type.title,
                    titleColor = titleColor,
                    onClick = { onMenuClick(type)}
                ){
                    when(type){
                        SettingMenuType.PUSH_NOTIFICATION -> {
                            DPlayToggle(
                                isChecked = state.isPushNotificationEnabled,
                                onClick = {
                                    onMenuClick(type)
                                }
                            )
                        }
                        SettingMenuType.VERSION -> {
                            Text(
                                // 임시 앱 버전
                                text = "v1.0.0",
                                style = DPlayTheme.typography.bodyMed14,
                                color = DPlayTheme.colors.gray400
                            )
                        }
                        SettingMenuType.LOGOUT -> {}
                        else -> {
                            DplayBaseIcon(
                                iconRes = R.drawable.ic_arrow_right_16
                            )
                        }
                    }
                }
            }else{
                Spacer(modifier = Modifier.weight(1f))

                DPlayUnderlineTextButton(
                    text = type.title,
                    onClick = { onMenuClick(type) }
                )
            }

            if(type == SettingMenuType.INQUIRY){
                HorizontalDivider(
                    thickness = 8.dp,
                    color = DPlayTheme.colors.gray100
                )
            }
        }
    }
}

@Composable
private fun SettingActionRow(
    actionName: String,
    modifier: Modifier = Modifier,
    titleColor: Color= DPlayTheme.colors.gray600,
    onClick: () -> Unit = {},
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable{ onClick() }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = actionName,
            color = titleColor,
            style = DPlayTheme.typography.bodySemi16
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
