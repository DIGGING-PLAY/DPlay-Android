package com.example.comment

import com.example.ui.base.BaseViewModel
import com.example.ui.model.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
@Inject constructor(): BaseViewModel<CommentContract.CommentState, CommentContract.CommentIntent, CommentContract.CommentSideEffect>(
    CommentContract.CommentState()
) {
    override fun handleIntent(intent: CommentContract.CommentIntent) {
        when(intent){
            is CommentContract.CommentIntent.Initialize -> {
                initializeMusicInfo(intent.music)
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
                // 가이드 노션으로 이동
            }
            CommentContract.CommentIntent.OnRegisterButtonClick -> {
                // 코멘트 등록 api 동작 후 Home으로 이동
                setSideEffect(CommentContract.CommentSideEffect.NavigateToHome)
            }
        }
    }
    private fun initializeMusicInfo(music: Music) {
        updateState {
            copy(musicInfo = music)
        }
    }
}