package com.example.home

import androidx.lifecycle.viewModelScope
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.domain.model.Badges
import com.example.domain.model.FeedItem
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import com.example.domain.repository.PostRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        val postRepository: PostRepository,
    ) : BaseViewModel<HomeContract.HomeState, HomeContract.HomeIntent, HomeContract.HomeSideEffect>(
            HomeContract.HomeState(),
        ) {
        override fun handleIntent(intent: HomeContract.HomeIntent) {
            when (intent) {
                is HomeContract.HomeIntent.LoadHomeData -> getTodayPosts()
                is HomeContract.HomeIntent.OnBookmarkClick -> toggleBookmark(intent.postId)
                is HomeContract.HomeIntent.OnLikeClick -> toggleLike(intent.postId)
                is HomeContract.HomeIntent.OnRefreshClick -> refreshTodayPosts()
                is HomeContract.HomeIntent.OnStreamClick -> previewStreaming(intent.trackId)
                is HomeContract.HomeIntent.OnListClick -> {
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToRecord)
                }

                is HomeContract.HomeIntent.OnWriterProfileClick -> {
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToWriterProfile(writerUserId = intent.writerUserId))
                }

                is HomeContract.HomeIntent.OnCoverClick -> {
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToPostDetail(postId = intent.postId))
                }
            }
        }

        private fun getTodayPosts() {
            viewModelScope.launch {
                postRepository
                    .getTodayPosts()
                    .onSuccess { data ->
                        updateState {
                            copy(
                                todayQuestion = data.todayQuestion,
                                hasPosted = data.hasPosted,
                                locked = data.locked,
                                totalCount = data.totalCount,
                                feedItems = data.todayPosts.toImmutableList(),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        private fun previewStreaming(trackId: String) {
            if (true) { // TODO: 미리듣기 API 미제공 게시물일 경우
                setSideEffect(
                    effect =
                        HomeContract.HomeSideEffect.ShowSnackBar(snackBarType = SnackBarType.STREAMING_NOT_SUPPORT, action = {
                            setSideEffect(HomeContract.HomeSideEffect.NavigateToMyPage)
                        }),
                )
            }
        }

        private fun refreshTodayPosts() {
        }

        private fun toggleBookmark(postId: Long) {
        }

        private fun toggleLike(postId: Long) {
        }
    }

val dummyFeedItems =
    persistentListOf(
        FeedItem(
            postId = 111,
            isScrapped = true,
            content = "그냥 좋아요 이 노래",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = true,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:203948",
                    songTitle = "Song Title 1",
                    coverImg = "https://picsum.photos/300",
                    artistName = "Artist1, Artist2",
                ),
            writer =
                Writer(
                    userId = 222,
                    nickname = "윤서암",
                    profileImg = "https://picsum.photos/200",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 24,
                ),
        ),
        FeedItem(
            postId = 112,
            isScrapped = false,
            content = "비 오는 날 꼭 듣는 노래에요",
            badges =
                Badges(
                    isEditorPick = true,
                    isPopular = false,
                    isNew = false,
                ),
            track =
                Track(
                    trackId = "apple:204837",
                    songTitle = "Song Title 2",
                    coverImg = "https://picsum.photos/310",
                    artistName = "Artist3",
                ),
            writer =
                Writer(
                    userId = 333,
                    nickname = "민석",
                    profileImg = "https://picsum.photos/201",
                ),
            like =
                Like(
                    isLiked = true,
                    count = 57,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistName = "Artist4",
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistName = "Artist4",
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistName = "Artist4",
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistName = "Artist4",
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
    )
