package com.example.detail

import androidx.lifecycle.viewModelScope
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.detail.DetailContract.DetailSideEffect.NavigateToMyPage
import com.example.detail.DetailContract.DetailSideEffect.ShowSnackBar
import com.example.domain.model.Like
import com.example.domain.repository.PostRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : BaseViewModel<DetailContract.DetailState, DetailContract.DetailIntent, DetailContract.DetailSideEffect>(
            DetailContract.DetailState(),
        ) {
        override fun handleIntent(intent: DetailContract.DetailIntent) {
            when (intent) {
                is DetailContract.DetailIntent.LoadData -> loadData(intent.postId, intent.date)
                is DetailContract.DetailIntent.OnBackButtonClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateBackStack)
                }

                is DetailContract.DetailIntent.OnBookmarkClick -> toggleBookmark()
                is DetailContract.DetailIntent.OnDeleteClick -> deletePost()
                is DetailContract.DetailIntent.OnLikeClick -> toggleLike()
                is DetailContract.DetailIntent.OnMeatBallsClick -> {
                    changeBottomSheetVisible(visible = true)
                }

                is DetailContract.DetailIntent.OnReportClick -> reportPost()
                is DetailContract.DetailIntent.OnStreamClick -> streamTrack()
                is DetailContract.DetailIntent.OnWriterProfileClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateToWriterProfile)
                }

                is DetailContract.DetailIntent.ChangeBottomSheetVisible -> {
                    changeBottomSheetVisible(visible = intent.visible)
                }
            }
        }

        private fun loadData(
            postId: Long,
            date: String,
        ) {
            viewModelScope.launch {
                postRepository
                    .getPostDetail(postId = 1)
                    .onSuccess { postDetail ->
                        updateState {
                            copy(
                                postId = postDetail.postId,
                                isScrapped = postDetail.isScrapped,
                                content = postDetail.content,
                                isHost = postDetail.isHost,
                                track = postDetail.track,
                                writer =
                                    postDetail.writer,
                                like =
                                    postDetail.like,
                                date = date,
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e, "error")
                    }
            }
        }

        private fun toggleBookmark() {
            viewModelScope.launch {
                val postId = currentState.postId
                val isScrapped = currentState.isScrapped

                val result =
                    if (isScrapped) {
                        postRepository.deletePostScrap(postId)
                    } else {
                        postRepository.postPostScrap(postId)
                    }

                result
                    .onSuccess {
                        updateState { copy(isScrapped = !isScrapped) }
                        if (!isScrapped) {
                            setSideEffect(
                                ShowSnackBar(
                                    snackBarType = SnackBarType.ADD,
                                    action = { setSideEffect(NavigateToMyPage) },
                                ),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        private fun toggleLike() {
            viewModelScope.launch {
                val postId = currentState.postId
                val isLiked = currentState.like.isLiked

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
                                like =
                                    Like(
                                        isLiked = !isLiked,
                                        count = newCount,
                                    ),
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e)
                    }
            }
        }

        private fun deletePost() {
            viewModelScope.launch {
                postRepository
                    .deletePost(postId = 2)
                    .onSuccess {
                        changeBottomSheetVisible(visible = false)
                    }.onFailure { e ->
                        changeBottomSheetVisible(visible = false)
                        Timber.e(e)
                    }
            }
        }

        private fun changeBottomSheetVisible(visible: Boolean) {
            updateState { copy(bottomSheetVisible = visible) }
        }

        private fun reportPost() {
        }

        private fun streamTrack() {
        }
    }
