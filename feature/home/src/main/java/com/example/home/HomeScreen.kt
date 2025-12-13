package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.common.model.FeedItem
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayLargeCover
import com.example.designsystem.component.DPlayMusicDiscItem
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.component.DplayClickableIcon
import com.example.designsystem.component.DplayLogoTopAppBar
import com.example.designsystem.theme.DPlayTheme
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeContract.HomeIntent.Initialize)
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is HomeContract.HomeSideEffect.ShowSnackBar -> {
                }
            }
        }
    }
    HomeScreen(
        uiState = state,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.HomeState = HomeContract.HomeState(),
) {
    val colors = DPlayTheme.colors
    val typography = DPlayTheme.typography
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DplayLogoTopAppBar(
            onListClick = {
                // TODO: [과거 추천 기록] 뷰로 이동
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = uiState.todayQuestion.dateText, style = typography.titleBold18, color = colors.dplayBlack)
            DplayClickableIcon(
                iconRes = R.drawable.ic_refresh_20,
                modifier = Modifier.padding(16.dp),
                onClick = {},
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        DPlaySubjectItem(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            subject = uiState.todayQuestion.title,
        )
        Spacer(modifier = Modifier.height(44.dp))
        HomePager(feedItems = uiState.feedItems)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePager(
    feedItems: List<FeedItem>,
    uiState: HomeContract.HomeState = HomeContract.HomeState(),
) {
    val pagerState = rememberPagerState(pageCount = { feedItems.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 40.dp),
        pageSpacing = 24.dp,
    ) { page ->

        val item = feedItems[page]

        val pageOffset =
            (
                (pagerState.currentPage - page) +
                    pagerState.currentPageOffsetFraction
            ).absoluteValue

        val isCenter = pageOffset < 0.2f

        val isLockedPage = uiState.locked && page >= 3

        if (isLockedPage) {
            Box {
                DPlayMusicDiscItem(
                    modifier = Modifier.fillMaxWidth(),
                    imageUrl = item.track.coverImg,
                )
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(shape = CircleShape)
                            .aspectRatio(1f)
                            .background(color = DPlayTheme.colors.dplayGrayTrans),
                    contentAlignment = Alignment.Center,
                ) {
                    DplayBaseIcon(
                        iconRes = R.drawable.ic_lock_42,
                    )
                }
            }
        } else {
            DPlayLargeCover(
                modifier = Modifier.fillMaxWidth(),
                isBookmarkChecked = item.isScrapped,
                isLikeChecked = item.like.isLiked,
                likeCount = item.like.count,
                writerProfileImageUrl = item.writer.profileImg,
                writerNickname = item.writer.nickname,
                content = item.content,
                musicImageUrl = item.track.coverImg,
                onStreamClick = {},
                onLikeClick = {},
                onBookmarkClick = {},
                onCoverClick = {},
                onWriterProfileClick = {},
                isStreaming = false,
                bookmarkIconVisible = isCenter,
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun HomePreview() {
    DPlayTheme {
        HomeScreen()
    }
}
