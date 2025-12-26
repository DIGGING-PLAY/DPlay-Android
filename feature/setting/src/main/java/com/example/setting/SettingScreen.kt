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
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SettingScreen(
        state = state,
    )
}

@Composable
fun SettingScreen(
    state: SettingContract.SettingState,
    modifier: Modifier = Modifier,
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
        ) {}

        SettingMenuType.entries.forEach { type ->
            val titleColor = if(type == SettingMenuType.LOGOUT) DPlayTheme.colors.alertRed else DPlayTheme.colors.gray600

            if(type != SettingMenuType.WITHDRAW){
                SettingActionRow(
                    type.title,
                    titleColor = titleColor,
                ){
                    when(type){
                        SettingMenuType.PUSH_NOTIFICATION -> {
                            DPlayToggle(
                                isChecked = state.isPushNotificationEnabled,
                                onClick = {}
                            )
                        }
                        SettingMenuType.VERSION -> {
                            Text(
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
                    onClick = {}
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
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(all = 16.dp),
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
