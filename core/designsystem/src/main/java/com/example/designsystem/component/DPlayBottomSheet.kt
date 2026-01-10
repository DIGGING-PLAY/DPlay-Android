package com.example.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.SubcomposeLayout
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
                ).clip(bottomSheetShape)
                .background(
                    color = DPlayTheme.colors.dplayWhite,
                    shape = bottomSheetShape,
                ).padding(16.dp),
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
    titleText: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
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
                ).clip(bottomSheetShape)
                .background(
                    color = DPlayTheme.colors.dplayWhite,
                    shape = bottomSheetShape,
                ).padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = titleText,
                style = DPlayTheme.typography.titleBold18,
                color = DPlayTheme.colors.dplayBlack,
            )
            Spacer(modifier = Modifier.weight(1f))
            DplayClickableIcon(
                iconRes = R.drawable.ic_close_24,
                onClick = onCloseClick,
                tint = DPlayTheme.colors.dplayBlack,
                modifier = Modifier.size(32.dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        content()

        Spacer(modifier = Modifier.height(12.dp))

        DPlayLargeGrayButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.5.dp),
            onClick = onButtonClick,
            label = buttonText,
        )
    }
}

@Composable
fun DPlayReportBottomSheet(
    onButtonClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCheckClick: (DPlayReportReason) -> Unit,
    modifier: Modifier = Modifier,
    reasons: List<DPlayReportReason> = DPlayReportReason.entries,
) {
    DPlayTitleButtonBottomSheet(
        titleText = stringResource(R.string.report_bottom_sheet_title),
        buttonText = "신고하기",
        onButtonClick = onButtonClick,
        onCloseClick = onCloseClick,
        modifier = modifier,
    ) {
        reasons.forEach { reason ->
            DPlayCheck(
                text = stringResource(reason.stringResId),
                isChecked = false,
                onClick = { onCheckClick(reason) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun DPlayDatePickerBottomSheet(
    initialYear: Int,
    initialMonth: Int,
    onApplyClick: (year: Int, month: Int) -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth) }

    DPlayTitleButtonBottomSheet(
        titleText = "날짜를 선택해주세요",
        buttonText = "적용하기",
        onButtonClick = { onApplyClick(selectedYear, selectedMonth) },
        onCloseClick = onCloseClick,
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        YearMonthWheelPicker(
            initialYear = selectedYear,
            initialMonth = selectedMonth,
            onYearSelected = { selectedYear = it },
            onMonthSelected = { selectedMonth = it },
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun YearMonthWheelPicker(
    initialYear: Int,
    initialMonth: Int,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    yearRange: IntRange = 2024..2026,
) {
    val years = yearRange.toList()
    val months = (1..12).toList()

    @Composable
    fun dateText(
        date: Int,
        modifier: Modifier = Modifier,
        isFocused: Boolean = false,
        isYear: Boolean = true,
    ) {
        Text(
            text = "$date${if (isYear) "년" else "월"}",
            modifier = modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            style = DPlayTheme.typography.bodySemi20,
            color = if (isFocused) DPlayTheme.colors.dplayBlack else DPlayTheme.colors.gray200,
        )
    }

    @Composable
    fun WheelColumn(
        items: List<Int>,
        initialIndex: Int,
        isYear: Boolean,
        onSelected: (Int) -> Unit,
    ) {
        var itemHeight by remember { mutableIntStateOf(0) }
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
        val focusedIndex by remember {
            derivedStateOf {
                val offset = listState.firstVisibleItemScrollOffset
                val index = listState.firstVisibleItemIndex
                if (offset > itemHeight / 2) index + 1 else index
            }
        }

        LaunchedEffect(listState.isScrollInProgress) {
            if (!listState.isScrollInProgress && itemHeight > 0) {
                val offset = listState.firstVisibleItemScrollOffset
                val targetIndex =
                    if (offset > itemHeight / 2) {
                        listState.firstVisibleItemIndex + 1
                    } else {
                        listState.firstVisibleItemIndex
                    }
                listState.animateScrollToItem(targetIndex.coerceIn(0, items.lastIndex))
                onSelected(items[targetIndex.coerceIn(0, items.lastIndex)])
            }
        }

        SubcomposeLayout { constraints ->
            val samplePlaceable =
                subcompose("sample") {
                    dateText(date = if (isYear) 2024 else 10, isYear = isYear)
                }.first().measure(constraints)

            val itemWidth = samplePlaceable.width
            itemHeight = samplePlaceable.height

            val boxPlaceable =
                subcompose("box") {
                    Box(
                        modifier = Modifier.width(itemWidth.toDp()),
                        contentAlignment = Alignment.Center,
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.height((itemHeight * 3).toDp()),
                            contentPadding = PaddingValues(vertical = itemHeight.toDp()),
                            flingBehavior = rememberSnapFlingBehavior(listState),
                        ) {
                            itemsIndexed(items) { index, item ->
                                dateText(
                                    date = item,
                                    isFocused = index == focusedIndex,
                                    isYear = isYear,
                                )
                            }
                        }
                        Column {
                            HorizontalDivider(thickness = 1.dp, color = DPlayTheme.colors.dplayBlack)
                            Spacer(modifier = Modifier.height(itemHeight.toDp()))
                            HorizontalDivider(thickness = 1.dp, color = DPlayTheme.colors.dplayBlack)
                        }
                    }
                }.first().measure(constraints)

            layout(boxPlaceable.width, boxPlaceable.height) {
                boxPlaceable.placeRelative(0, 0)
            }
        }
    }

    Row(modifier = modifier) {
        WheelColumn(
            items = years,
            initialIndex = years.indexOf(initialYear).coerceAtLeast(0),
            isYear = true,
            onSelected = onYearSelected,
        )

        Spacer(modifier = Modifier.width(44.dp))

        WheelColumn(
            items = months,
            initialIndex = (initialMonth - 1).coerceAtLeast(0),
            isYear = false,
            onSelected = onMonthSelected,
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
                subOnClick = {},
            )

            DPlayButtonBottomSheet(
                mainText = "삭제하기",
                subText = "취소하기",
                mainOnClick = {},
                subOnClick = {},
                mainButtonColor = DPlayTheme.colors.dplayPink,
            )
            DPlayReportBottomSheet(
                onCloseClick = {},
                onButtonClick = {},
                onCheckClick = {},
            )
            DPlayDatePickerBottomSheet(
                onCloseClick = {},
                onApplyClick = { year, month ->
                },
                initialYear = 2026,
                initialMonth = 2,
            )
        }
    }
}
