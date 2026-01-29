package com.example.home

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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayLargeCover
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayClickableIcon
import com.example.designsystem.component.DplayLogoTopAppBar
import com.example.designsystem.component.button.DPlayBookmarkButton
import com.example.designsystem.component.chip.DPlayChip
import com.example.designsystem.component.chip.type.DPlayChipType
import com.example.designsystem.component.snackbar.LocalShowSnackBar
import com.example.designsystem.theme.DPlayTheme
import com.example.domain.model.BADGE
import com.example.domain.model.FeedItem
import com.example.navigation.Detail
import com.example.navigation.MyPage
import com.example.navigation.MyPageTab
import com.example.navigation.Navigator
import com.example.navigation.Record
import com.example.navigation.Search
import com.example.ui.controller.LocalModalController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    navigator: Navigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val showSnackBar = LocalShowSnackBar.current
    val modalController = LocalModalController.current
    val pagerState = rememberPagerState(pageCount = { state.feedItems.size })

    val lockedModalMainText = stringResource(R.string.recommend_prompt_modal_main_text)
    val lockedModalSubText = stringResource(R.string.recommend_prompt_modal_sub_text)
    val lockedModalButtonLabel = stringResource(R.string.recommend_prompt_modal_button_label)

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
                            badge = it.badge,
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
                    navigator.navigateTo(destination = MyPage(initialTab = MyPageTab.BOOKMARKED))
                }

                is HomeContract.HomeSideEffect.ShowLockedModal -> {
                    modalController.showGraphicModal(
                        mainText = lockedModalMainText,
                        subText = lockedModalSubText,
                        buttonLabel = lockedModalButtonLabel,
                        onButtonClick = {
                            modalController.hideModal()
                            navigator.navigateTo(destination = Search)
                        },
                        onDismiss = {
                            modalController.hideModal()
                        },
                    )
                }

                is HomeContract.HomeSideEffect.ScrollToFirstPage -> {
                    pagerState.animateScrollToPage(0)
                }
            }
        }
    }
    HomeScreen(
        uiState = state,
        pagerState = pagerState,
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
        onLockedCoverClick = {
            viewModel.handleIntent(HomeContract.HomeIntent.OnLockedCoverClick)
        },
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.HomeState = HomeContract.HomeState(),
    pagerState: PagerState,
    onRefresh: () -> Unit,
    onPostClick: (postId: Long) -> Unit,
    onBookmarkClick: (postId: Long) -> Unit,
    onStreamClick: (trackId: String) -> Unit,
    onLikeClick: (postId: Long) -> Unit,
    onWriterProfileClick: (Long) -> Unit,
    onListClick: () -> Unit,
    onLockedCoverClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DplayLogoTopAppBar(onListClick = onListClick)
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
                modifier = Modifier.padding(8.dp),
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
            pagerState = pagerState,
            feedItems = uiState.feedItems,
            onPostClick = onPostClick,
            onBookmarkClick = onBookmarkClick,
            onStreamClick = onStreamClick,
            onLikeClick = onLikeClick,
            onWriterProfileClick = onWriterProfileClick,
            onLockedCoverClick = onLockedCoverClick,
            uiState = uiState,
        )
    }
}

@Composable
private fun HomePager(
    pagerState: PagerState,
    feedItems: List<FeedItem>,
    onPostClick: (postId: Long) -> Unit,
    onBookmarkClick: (postId: Long) -> Unit,
    onStreamClick: (trackId: String) -> Unit,
    onLikeClick: (postId: Long) -> Unit,
    onWriterProfileClick: (Long) -> Unit,
    onLockedCoverClick: () -> Unit,
    uiState: HomeContract.HomeState,
) {
    val currentItem = feedItems.getOrNull(pagerState.currentPage)
    val isCurrentPageLocked = uiState.locked && pagerState.currentPage >= 3
    val currentChipType: DPlayChipType? =
        currentItem?.badge?.let {
            when (it) {
                BADGE.BEST -> DPlayChipType.BEST
                BADGE.EDITOR -> DPlayChipType.EDITOR
                BADGE.NEW -> DPlayChipType.NEW
            }
        }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(52.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 40.dp),
                pageSpacing = 24.dp,
            ) { page ->
                val item = feedItems[page]
                val isLockedPage = uiState.locked && page >= 3

                DPlayLargeCover(
                    modifier = Modifier.fillMaxWidth(),
                    isLocked = isLockedPage,
                    isLikeChecked = item.like.isLiked,
                    likeCount = item.like.count,
                    writerProfileImageUrl = item.writer.profileImg,
                    writerNickname = item.writer.nickname,
                    content = item.content,
                    musicImageUrl = item.track.coverImg,
                    onStreamClick = { onStreamClick(item.track.trackId) },
                    onLikeClick = { onLikeClick(item.postId) },
                    onCoverClick = {
                        if (isLockedPage) {
                            onLockedCoverClick()
                        } else {
                            onPostClick(item.postId)
                        }
                    },
                    onWriterProfileClick = { onWriterProfileClick(item.writer.userId) },
                    isStreaming = uiState.streamingTrackId == item.track.trackId,
                )
            }
        }

        currentChipType
            ?.takeIf { !isCurrentPageLocked }
            ?.let {
                DPlayChip(
                    type = it,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }

        currentItem
            ?.takeIf { !isCurrentPageLocked }
            ?.let {
                DPlayBookmarkButton(
                    isMarked = it.isScrapped,
                    onClick = { onBookmarkClick(it.postId) },
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 52.dp, end = 40.dp),
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
        HomeScreen(
            pagerState = rememberPagerState(pageCount = { 0 }),
            onPostClick = {},
            onBookmarkClick = {},
            onStreamClick = {},
            onLikeClick = {},
            onWriterProfileClick = {},
            onRefresh = {},
            onListClick = {},
            onLockedCoverClick = {},
        )
    }
}
