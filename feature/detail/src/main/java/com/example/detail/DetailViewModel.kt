package com.example.detail

import com.example.common.model.Badges
import com.example.common.model.Like
import com.example.common.model.Track
import com.example.common.model.Writer
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.detail.DetailContract.DetailSideEffect.NavigateToMyPage
import com.example.detail.DetailContract.DetailSideEffect.ShowSnackBar
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor() : BaseViewModel<DetailContract.DetailState, DetailContract.DetailIntent, DetailContract.DetailSideEffect>(
            DetailContract.DetailState(),
        ) {
        override fun handleIntent(intent: DetailContract.DetailIntent) {
            when (intent) {
                is DetailContract.DetailIntent.LoadData -> loadData(intent.postId)
                is DetailContract.DetailIntent.OnBackButtonClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateBackStack)
                }

                is DetailContract.DetailIntent.OnBookmarkClick -> toggleBookmark()
                is DetailContract.DetailIntent.OnDeleteClick -> deletePost()
                is DetailContract.DetailIntent.OnLikeClick -> toggleLike()
                is DetailContract.DetailIntent.OnMeatBallsClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.ShowBottomSheet)
                }

                is DetailContract.DetailIntent.OnReportClick -> reportPost()
                is DetailContract.DetailIntent.OnStreamClick -> streamTrack()
                is DetailContract.DetailIntent.OnWriterProfileClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateToWriterProfile)
                }
            }
        }

        private fun loadData(postId: Long) {
            updateState {
                copy(
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
                            artistName = "Artist1,Artist2",
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
                    date = "2025-10-19",
                )
            }
        }

        private fun toggleBookmark() {
            updateState { copy(isScrapped = !currentState.isScrapped) }
            setSideEffect(
                ShowSnackBar(
                    snackBarType = SnackBarType.ADD,
                    action = {
                        setSideEffect(NavigateToMyPage)
                    },
                ),
            )
        }

        private fun toggleLike() {
            val newCount =
                if (currentState.like.isLiked) {
                    currentState.like.count - 1
                } else {
                    currentState.like.count + 1
                }
            updateState { copy(like = Like(isLiked = !currentState.like.isLiked, count = newCount)) }
        }

        private fun deletePost() {
        }

        private fun reportPost() {
        }

        private fun streamTrack() {
        }
    }
