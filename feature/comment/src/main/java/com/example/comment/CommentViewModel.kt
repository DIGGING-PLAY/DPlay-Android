package com.example.comment

import androidx.lifecycle.viewModelScope
import com.example.common.constant.Url
import com.example.common.event.HomeRefreshTrigger
import com.example.domain.repository.PostRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.model.TrackState
import com.example.ui.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val homeRefreshTrigger: HomeRefreshTrigger,
    ) : BaseViewModel<CommentContract.CommentState, CommentContract.CommentIntent, CommentContract.CommentSideEffect>(
            CommentContract.CommentState(),
        ) {
        override fun handleIntent(intent: CommentContract.CommentIntent) {
            when (intent) {
                is CommentContract.CommentIntent.Initialize -> {
                    initializeMusicInfo(intent.track)
                }
                CommentContract.CommentIntent.OnBackIconClick -> {
                    setSideEffect(CommentContract.CommentSideEffect.NavigateToBack)
                }
                is CommentContract.CommentIntent.OnCommentInputChanged -> {
                    updateState {
                        copy(commentInput = intent.commentInput.trimStart())
                    }
                }
                CommentContract.CommentIntent.OnGuideButtonClick -> {
                    updateState {
                        copy(isGuideVisible = true)
                    }
                }
                CommentContract.CommentIntent.OnGuideXIconClick -> {
                    // guide의 dismiss 동작은 x 버튼 밖에 없음
                    updateState {
                        copy(isGuideVisible = false)
                    }
                }
                CommentContract.CommentIntent.OnMoreGuideClick -> {
                    setSideEffect(CommentContract.CommentSideEffect.OpenWebView(Url.COMMUNITY_GUIDE))
                }
                CommentContract.CommentIntent.OnRegisterButtonClick -> {
                    registerPost()
                }
            }
        }

        private fun registerPost() {
            viewModelScope.launch {
                val track = currentState.track?.toDomain() ?: return@launch

                postRepository
                    .registerPost(
                        track = track,
                        comment = currentState.commentInput,
                    ).onSuccess {
                        homeRefreshTrigger.refresh()
                        setSideEffect(CommentContract.CommentSideEffect.NavigateToHome)
                    }.onFailure {
                    }
            }
        }

        private fun initializeMusicInfo(trackState: TrackState) {
            updateState {
                copy(track = trackState)
            }
        }
    }
