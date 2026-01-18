package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayLargeCover
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayClickableIcon
import com.example.designsystem.component.DplayLogoTopAppBar
import com.example.designsystem.component.chip.DPlayChip
import com.example.designsystem.component.chip.type.DPlayChipType
import com.example.designsystem.component.snackbar.LocalShowSnackBar
import com.example.designsystem.theme.DPlayTheme
import com.example.domain.model.FeedItem
import com.example.navigation.Detail
import com.example.navigation.Navigator
import com.example.navigation.Record
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Composable
fun HomeRoute(
    navigator: Navigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val showSnackBar = LocalShowSnackBar.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeContract.HomeIntent.LoadHomeData)
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is HomeContract.HomeSideEffect.NavigateToRecord -> {
                    navigator.navigateTo(destination = Record)
                }

                is HomeContract.HomeSideEffect.NavigateToPostDetail -> {
                    navigator.navigateTo(
                        Detail(
                            postId = it.postId,
                            date = state.todayQuestion.recordMMDD,
                        ),
                    )
                }

                is HomeContract.HomeSideEffect.NavigateToWriterProfile -> {
                    // TODO
                }

                is HomeContract.HomeSideEffect.ShowSnackBar -> {
                    showSnackBar(it.snackBarType, it.action)
                }

                is HomeContract.HomeSideEffect.NavigateToMyPage -> {
                    // TODO
                }
            }
        }
    }
    HomeScreen(
        uiState = state,
        onRefresh = {
            viewModel.handleIntent(HomeContract.HomeIntent.OnRefreshClick)
        },
        onPostClick = { postId ->
            viewModel.handleIntent(HomeContract.HomeIntent.OnCoverClick(postId = postId))
        },
        onBookmarkClick = { postId ->
            viewModel.handleIntent(HomeContract.HomeIntent.OnBookmarkClick(postId = postId))
        },
        onStreamClick = { trackId ->
            viewModel.handleIntent(HomeContract.HomeIntent.OnStreamClick(trackId = trackId))
        },
        onLikeClick = { postId ->
            viewModel.handleIntent(HomeContract.HomeIntent.OnLikeClick(postId = postId))
        },
        onWriterProfileClick = { writerUserId ->
            viewModel.handleIntent(HomeContract.HomeIntent.OnWriterProfileClick(writerUserId = writerUserId))
        },
        onListClick = {
            viewModel.handleIntent(HomeContract.HomeIntent.OnListClick)
        },
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.HomeState = HomeContract.HomeState(),
    onRefresh: () -> Unit,
    onPostClick: (postId: Long) -> Unit,
    onBookmarkClick: (postId: Long) -> Unit,
    onStreamClick: (trackId: String) -> Unit,
    onLikeClick: (postId: Long) -> Unit,
    onWriterProfileClick: (Long) -> Unit,
    onListClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DplayLogoTopAppBar(onListClick = onListClick)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = uiState.todayQuestion.homeTitleDateText, style = DPlayTheme.typography.titleBold18, color = DPlayTheme.colors.dplayBlack)
            DplayClickableIcon(
                iconRes = R.drawable.ic_refresh_20,
                modifier = Modifier.padding(16.dp),
                onClick = onRefresh,
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
        Spacer(modifier = Modifier.height(32.dp))
        HomePager(
            feedItems = uiState.feedItems,
            onPostClick = onPostClick,
            onBookmarkClick = onBookmarkClick,
            onStreamClick = onStreamClick,
            onLikeClick = onLikeClick,
            onWriterProfileClick = onWriterProfileClick,
            uiState = uiState,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePager(
    feedItems: List<FeedItem>,
    onPostClick: (postId: Long) -> Unit,
    onBookmarkClick: (postId: Long) -> Unit,
    onStreamClick: (trackId: String) -> Unit,
    onLikeClick: (postId: Long) -> Unit,
    onWriterProfileClick: (Long) -> Unit,
    uiState: HomeContract.HomeState,
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
        val chipType: DPlayChipType? =
            when {
                item.badges.isPopular -> DPlayChipType.BEST
                item.badges.isEditorPick -> DPlayChipType.EDITOR
                item.badges.isNew -> DPlayChipType.NEW
                else -> null
            }

        Box {
            chipType
                ?.takeIf { !isLockedPage }
                ?.let {
                    DPlayChip(
                        type = it,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            Column {
                Spacer(modifier = Modifier.height(52.dp))
                DPlayLargeCover(
                    modifier = Modifier.fillMaxWidth(),
                    isLocked = isLockedPage,
                    isBookmarkChecked = item.isScrapped,
                    isLikeChecked = item.like.isLiked,
                    likeCount = item.like.count,
                    writerProfileImageUrl = item.writer.profileImg,
                    writerNickname = item.writer.nickname,
                    content = item.content,
                    musicImageUrl = item.track.coverImg,
                    onStreamClick = { onStreamClick(item.track.trackId) },
                    onLikeClick = { onLikeClick(item.postId) },
                    onBookmarkClick = { onBookmarkClick(item.postId) },
                    onCoverClick = { onPostClick(item.postId) },
                    onWriterProfileClick = { onWriterProfileClick(item.writer.userId) },
                    isStreaming = false,
                    bookmarkIconVisible = isCenter,
                )
            }
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
        HomeScreen(
            onPostClick = {},
            onBookmarkClick = {},
            onStreamClick = {},
            onLikeClick = {},
            onWriterProfileClick = {},
            onRefresh = {},
            onListClick = {},
        )
    }
}
