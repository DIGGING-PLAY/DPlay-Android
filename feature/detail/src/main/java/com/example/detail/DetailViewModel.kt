package com.example.detail


import androidx.lifecycle.viewModelScope
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.detail.DetailContract.DetailSideEffect.NavigateToMyPage
import com.example.detail.DetailContract.DetailSideEffect.ShowSnackBar
import com.example.domain.model.Badges
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import com.example.domain.repository.PostRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        private fun loadData(postId: Long) {
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
                                date = postDetail.date,
                            )
                        }
                    }.onFailure { e ->
                        Timber.e(e, "error")
                    }
            }
        }

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
