package com.example.designsystem.component.modal

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.button.ModalButton
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DualActionModal(
    mainText: String,
    leftButtonLabel: String,
    rightButtonLabel: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    onLeftButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {}
){
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = DPlayTheme.colors.dplayWhite,
                shape = RoundedCornerShape(16.dp)
            )
    ){
      Column(
          horizontalAlignment = Alignment.CenterHorizontally
      ){
          ModalContent(
              mainText = mainText,
              subText = subText
          )

          ActionRow(
              leftButtonLabel = leftButtonLabel,
              rightButtonLabel = rightButtonLabel,
              onLeftButtonClick = onLeftButtonClick,
              onRightButtonClick = onRightButtonClick
          )
      }
    }
}

@Composable
private fun ModalContent(
    mainText: String,
    subText: String?
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 16.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_warning_40),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = mainText,
            style = DPlayTheme.typography.bodyBold16,
            color = DPlayTheme.colors.dplayBlack,
            textAlign = TextAlign.Center
        )

        if (subText != null) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subText,
                style = DPlayTheme.typography.bodyMed14,
                color = DPlayTheme.colors.gray400,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionRow(
    leftButtonLabel: String,
    rightButtonLabel: String,
    onLeftButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ModalButton(
            modifier = Modifier.weight(1f),
            backgroundColor = DPlayTheme.colors.gray100,
            onClick = onLeftButtonClick
        ){
            Text(
                text = leftButtonLabel,
                style = DPlayTheme.typography.bodySemi14,
                color = DPlayTheme.colors.gray400
            )
        }

        ModalButton(
            modifier = Modifier.weight(1f),
            backgroundColor = DPlayTheme.colors.gray600,
            onClick = onRightButtonClick
        ) {
            Text(
                text = rightButtonLabel,
                style = DPlayTheme.typography.bodySemi14,
                color = DPlayTheme.colors.dplayWhite
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DualActionModalPreview() {
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = DPlayTheme.colors.dplayBlack
                )
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DualActionModal(
                mainText = stringResource(R.string.delete_modal_main_text),
                leftButtonLabel = stringResource(R.string.delete_modal_cancle_button_label),
                rightButtonLabel = stringResource(R.string.delete_modal_delete_button_label)
            )

            DualActionModal(
                mainText = stringResource(R.string.logout_modal_main_Text),
                leftButtonLabel = stringResource(R.string.logout_modal_cancel_button_label),
                rightButtonLabel = stringResource(R.string.logout_modal_logout_button_label)
            )

            DualActionModal(
                mainText = stringResource(R.string.withdraw_modal_main_text),
                subText = stringResource(R.string.withdraw_modal_sub_text),
                leftButtonLabel = stringResource(R.string.withdraw_modal_withdraw_button_label),
                rightButtonLabel = stringResource(R.string.withdraw_modal_cancel_button_label)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModalInteractionPreview(){
    var isVisible by remember { mutableStateOf(false) }

    BackHandler(enabled = isVisible) {
        isVisible = false
    }

    DPlayTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
        ){
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = { isVisible = true }) {
                    Text("모달 열기")
                }
                Button(onClick = { isVisible = true }) {
                    Text("다른 모달 열기")
                }
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DPlayTheme.colors.dplayBlack.copy(alpha = 0.6f))
                        .noRippleClickable { isVisible = false }
                )
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(200)) + scaleIn(initialScale = 0.5f, animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(150)) + scaleOut(targetScale = 0.97f, animationSpec = tween(150))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DualActionModal(
                        mainText = stringResource(R.string.withdraw_modal_main_text),
                        subText = stringResource(R.string.withdraw_modal_sub_text),
                        leftButtonLabel = stringResource(R.string.withdraw_modal_withdraw_button_label),
                        rightButtonLabel = stringResource(R.string.withdraw_modal_cancel_button_label),
                        onLeftButtonClick = { isVisible = false },
                        onRightButtonClick = { isVisible = false }
                    )
                }
            }
        }
    }
}