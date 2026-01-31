package com.example.home

import androidx.lifecycle.viewModelScope
import com.example.common.audio.AudioPlayer
import com.example.common.event.HomeRefreshTrigger
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.domain.model.FeedItem
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.UserRelation
import com.example.domain.model.Writer
import com.example.domain.repository.PostRepository
import com.example.domain.repository.TrackRepository
import com.example.domain.usecase.CheckUserRelationUseCase
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val trackRepository: TrackRepository,
        private val audioPlayer: AudioPlayer,
        private val homeRefreshTrigger: HomeRefreshTrigger,
        private val checkUserRelationUseCase: CheckUserRelationUseCase,
    ) : BaseViewModel<HomeContract.HomeState, HomeContract.HomeIntent, HomeContract.HomeSideEffect>(
            HomeContract.HomeState(),
        ) {
        init {
            observePlaybackState()
            observeRefreshTrigger()
            getTodayPosts()
        }

        private fun observePlaybackState() {
            audioPlayer.playbackState
                .onEach { playbackState ->
                    updateState {
                        copy(
                            streamingTrackId =
                                if (playbackState.isPlaying) {
                                    playbackState.currentTrackId
                                } else {
                                    null
                                },
                        )
                    }
                }.launchIn(viewModelScope)
        }

        private fun observeRefreshTrigger() {
            homeRefreshTrigger.refreshEvent
                .onEach {
                    getTodayPosts()
                }.launchIn(viewModelScope)
        }

        override fun handleIntent(intent: HomeContract.HomeIntent) {
            when (intent) {
                is HomeContract.HomeIntent.OnBookmarkClick -> toggleBookmark(intent.postId)
                is HomeContract.HomeIntent.OnLikeClick -> toggleLike(intent.postId)
                is HomeContract.HomeIntent.OnRefreshClick -> refreshTodayPosts()
                is HomeContract.HomeIntent.OnStreamClick -> previewStreaming(intent.trackId)
                is HomeContract.HomeIntent.OnListClick -> {
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToRecord)
                }

                is HomeContract.HomeIntent.OnWriterProfileClick -> {
                    navigateToOthersProfile(intent.writerUserId)
                }

                is HomeContract.HomeIntent.OnCoverClick -> {
                    val badge = currentState.feedItems.find { it.postId == intent.postId }?.badge
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToPostDetail(postId = intent.postId, badge = badge))
                }

                is HomeContract.HomeIntent.OnLockedCoverClick -> {
                    setSideEffect(HomeContract.HomeSideEffect.ShowLockedModal)
                }
            }
        }

        private fun getTodayPosts() {
            viewModelScope.launch {
                postRepository
                    .getTodayPosts()
                    .onSuccess { data ->
                        val feedItems =
                            if (data.locked) {
                                data.todayPosts + lockedDummyFeedItem
                            } else {
                                data.todayPosts
                            }
                        updateState {
                            copy(
                                todayQuestion = data.todayQuestion,
                                hasPosted = data.hasPosted,
                                locked = data.locked,
                                totalCount = data.totalCount,
                                feedItems = feedItems.toImmutableList(),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        companion object {
            private val lockedDummyFeedItem =
                FeedItem(
                    postId = -1L,
                    isScrapped = false,
                    content = "",
                    badge = null,
                    track =
                        Track(
                            trackId = "",
                            songTitle = "",
                            coverImg = "",
                            artistName = "",
                            isrc = "",
                        ),
                    writer =
                        Writer(
                            userId = -1L,
                            nickname = "",
                            profileImg = "",
                            isAdmin = false,
                        ),
                    like =
                        Like(
                            isLiked = false,
                            count = 0,
                        ),
                )
        }

        private fun previewStreaming(trackId: String) {
            val currentStreamingTrackId = audioPlayer.playbackState.value.currentTrackId
            if (currentStreamingTrackId == trackId && audioPlayer.playbackState.value.isPlaying) {
                audioPlayer.stop()
                return
            }

            val feedItem = currentState.feedItems.find { it.track.trackId == trackId }

            viewModelScope.launch {
                trackRepository
                    .getTrackPreview(trackId = trackId)
                    .onSuccess { preview ->
                        audioPlayer.play(
                            url = preview.streamUrl,
                            trackId = trackId,
                            title = feedItem?.track?.songTitle ?: "",
                            artist = feedItem?.track?.artistName ?: "",
                        )
                    }.onFailure { e ->
                        Timber.e(e)
                        setSideEffect(
                            HomeContract.HomeSideEffect.ShowSnackBar(
                                snackBarType = SnackBarType.STREAMING_NOT_SUPPORT,
                            ),
                        )
                    }
            }
        }

        private fun refreshTodayPosts() {
            getTodayPosts()
            setSideEffect(HomeContract.HomeSideEffect.ScrollToFirstPage)
        }

        private fun toggleBookmark(postId: Long) {
            viewModelScope.launch {
                val feedItem = currentState.feedItems.find { it.postId == postId } ?: return@launch
                val isScrapped = feedItem.isScrapped

                val result =
                    if (isScrapped) {
                        postRepository.deletePostScrap(postId)
                    } else {
                        postRepository.postPostScrap(postId)
                    }

                result
                    .onSuccess {
                        updateState {
                            copy(
                                feedItems =
                                    feedItems
                                        .map { item ->
                                            if (item.postId == postId) {
                                                item.copy(isScrapped = !isScrapped)
                                            } else {
                                                item
                                            }
                                        }.toImmutableList(),
                            )
                        }
                        if (!isScrapped) {
                            setSideEffect(
                                HomeContract.HomeSideEffect.ShowSnackBar(
                                    snackBarType = SnackBarType.ADD,
                                    action = { setSideEffect(HomeContract.HomeSideEffect.NavigateToMyPage) },
                                ),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        private fun toggleLike(postId: Long) {
            viewModelScope.launch {
                val feedItem = currentState.feedItems.find { it.postId == postId } ?: return@launch
                val isLiked = feedItem.like.isLiked

                val result =
                    if (isLiked) {
                        postRepository.deletePostLike(postId)
                    } else {
                        postRepository.postPostLike(postId)
                    }

                result
                    .onSuccess { newCount ->
                        updateState {
                            copy(
                                feedItems =
                                    feedItems
                                        .map { item ->
                                            if (item.postId == postId) {
                                                item.copy(
                                                    like =
                                                        Like(
                                                            isLiked = !isLiked,
                                                            count = newCount,
                                                        ),
                                                )
                                            } else {
                                                item
                                            }
                                        }.toImmutableList(),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        private fun navigateToOthersProfile(userId: Long) {
            viewModelScope.launch {
                val userRelation = checkUserRelationUseCase(userId)
                if (userRelation == UserRelation.OTHER) {
                    setSideEffect(HomeContract.HomeSideEffect.NavigateToWriterProfile(userId))
                }
            }
        }
    }
