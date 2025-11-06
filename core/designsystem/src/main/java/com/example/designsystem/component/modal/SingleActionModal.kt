package com.example.designsystem.component.modal

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
fun SingleActionModal(
    mainText: String,
    buttonLabel: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    onCloseIconClick: () -> Unit = {},
    onButtonClick: () -> Unit = {},
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
                subText = subText,
                onCloseIconClick = onCloseIconClick
            )

            ModalButton(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DPlayTheme.colors.gray600,
                onClick = onButtonClick
            ){
                Text(
                    text = buttonLabel,
                    style = DPlayTheme.typography.bodySemi14,
                    color = DPlayTheme.colors.dplayWhite
                )
            }
        }
    }
}

@Composable
private fun ModalContent(
    mainText: String,
    subText: String?,
    onCloseIconClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 16.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Spacer(modifier = Modifier.size(32.dp))

            Image(
                painter = painterResource(R.drawable.img_key),
                contentDescription = null,
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .noRippleClickable(onClick = onCloseIconClick),
                contentAlignment = Alignment.Center,
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                    contentDescription = "모달창 닫기",
                    tint = DPlayTheme.colors.dplayBlack,
                )
            }
        }

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

@Preview(showBackground = true)
@Composable
fun SingleActionModalPreview() {
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
            SingleActionModal(
                mainText = stringResource(R.string.recommend_prompt_modal_main_text),
                subText = stringResource(R.string.recommend_prompt_modal_sub_text),
                buttonLabel = stringResource(R.string.recommend_prompt_modal_button_label),
            )
        }
    }
}
