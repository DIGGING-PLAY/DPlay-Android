package com.example.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.button.DPlayLargeGrayButton
import com.example.designsystem.component.button.DPlayUnderlineTextButton
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

enum class DPlayReportReason(
    @param:StringRes val stringResId: Int,
) {
    INAPPROPRIATE_CONTENT(R.string.report_reason_inappropriate_content),
    OFFENSIVE_EXPRESSION(R.string.report_reason_offensive_expression),
    SUSPICIOUS_OR_SPAM(R.string.report_reason_suspicious_or_spam),
    COPYRIGHT_VIOLATION(R.string.report_reason_copyright_violation),
}

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
        modifier =
            modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = bottomSheetShape,
                    shadow =
                        Shadow(
                            radius = 20.dp,
                            alpha = 0.15f,
                        ),
                )
                .clip(bottomSheetShape)
                .background(
                    color = DPlayTheme.colors.dplayWhite,
                    shape = bottomSheetShape,
                )
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = mainText,
            style = DPlayTheme.typography.bodySemi16,
            color = mainButtonColor,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .noRippleClickable(onClick = mainOnClick),
            textAlign = TextAlign.Center,
        )
        DPlayUnderlineTextButton(
            onClick = subOnClick,
            text = subText,
        )
    }
}

@Composable
fun DPlayTitleButtonBottomSheet(
    onButtonClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCheckClick: (DPlayReportReason) -> Unit,
    modifier: Modifier = Modifier,
    reasons: List<DPlayReportReason> = DPlayReportReason.entries,
    titleText: String = stringResource(R.string.report_bottom_sheet_title),
) {
    val bottomSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = bottomSheetShape,
                    shadow =
                        Shadow(
                            radius = 20.dp,
                            alpha = 0.15f,
                        ),
                )
                .clip(bottomSheetShape)
                .background(
                    color = DPlayTheme.colors.dplayWhite,
                    shape = bottomSheetShape,
                )
                .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = titleText, style = DPlayTheme.typography.titleBold18, color = DPlayTheme.colors.dplayBlack)
            Spacer(modifier = Modifier.weight(1f))
            DplayClickableIcon(
                iconRes = R.drawable.ic_close_24,
                onClick = onCloseClick,
                tint = DPlayTheme.colors.dplayBlack,
                modifier = Modifier.size(32.dp),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        reasons.forEach { reason ->
            DPlayCheck(
                text = stringResource(reason.stringResId),
                isChecked = false,
                onClick = { onCheckClick(reason) },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        DPlayLargeGrayButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.5.dp),
            onClick = onButtonClick,
            label = "신고하기",
        )
    }
}

@Preview
@Composable
private fun DPlayBottomSheetPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = DPlayTheme.colors.dplayWhite),
        ) {
            DPlayButtonBottomSheet(
                mainText = stringResource(R.string.launch_album_bottomsheet_main_text),
                subText = stringResource(R.string.launch_album_bottomsheet_sub_text),
                mainOnClick = {},
                subOnClick = {}
            )

            DPlayButtonBottomSheet(
                mainText = "삭제하기",
                subText = "취소하기",
                mainOnClick = {},
                subOnClick = {},
                mainButtonColor = DPlayTheme.colors.dplayPink,
            )
            DPlayTitleButtonBottomSheet(
                onCloseClick = {},
                onButtonClick = {},
                onCheckClick = {},
            )
        }
    }
}
