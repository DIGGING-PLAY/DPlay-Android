package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.icon.DplayClickableIcon
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayButtonBottomSheet(
    mainText: String,
    subText: String,
    mainOnClick: () -> Unit,
    subOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    mainButtonColor: Color = DPlayTheme.colors.dplayBlack,
) {
    val bottomSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = bottomSheetShape, shadow = Shadow(
                    radius = 20.dp, alpha = 0.15f
                )
            )
            .clip(bottomSheetShape)
            .background(
                color = DPlayTheme.colors.dplayWhite,
                shape = bottomSheetShape,
            )
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mainText,
            style = DPlayTheme.typography.bodySemi16,
            color = mainButtonColor,
            modifier = Modifier.fillMaxWidth().padding(16.dp).noRippleClickable(onClick = mainOnClick)
        )

        //TODO: 버튼 머지되면 UnderlineTextButton으로 교체
        Text(text = subText, style = DPlayTheme.typography.capMed12, color = DPlayTheme.colors.gray400, modifier = Modifier.noRippleClickable(subOnClick))
    }
}


@Composable
fun DPlayTitleButtonBottomSheet(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleText: String = stringResource(R.string.report_bottom_sheet_title)
) {
    val bottomSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = bottomSheetShape, shadow = Shadow(
                    radius = 20.dp, alpha = 0.15f
                )
            )
            .clip(bottomSheetShape)
            .background(
                color = DPlayTheme.colors.dplayWhite,
                shape = bottomSheetShape,
            )
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = titleText, style = DPlayTheme.typography.titleBold18, color = DPlayTheme.colors.dplayBlack)
            Spacer(modifier = Modifier.weight(1f))
            DplayClickableIcon(
                iconRes = R.drawable.ic_close_24,
                onClick = onCloseClick
            )
        }
    }
}

@Preview
@Composable
private fun DPlayBottomSheetPreview() {
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
        ) {
            DPlayButtonBottomSheet(mainText = "앨범에서 선택하기", subText = "기본 이미지로 변경하기", mainOnClick = {}, subOnClick = {})

            DPlayButtonBottomSheet(
                mainText = "삭제하기",
                subText = "취소하기",
                mainOnClick = {},
                subOnClick = {},
                mainButtonColor = DPlayTheme.colors.dplayPink
            )
            DPlayTitleButtonBottomSheet(
                onCloseClick = {}
            )
        }
    }
}