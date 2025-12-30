package com.example.comment

import com.example.ui.base.BaseContract
import com.example.ui.model.Music

class CommentContract {
    data class CommentState(
        val musicInfo: Music? = null,
        val commentInput: String = "",
        val isGuideVisible: Boolean = false,
    ): BaseContract.State{
        val isRegisterButtonEnabled: Boolean
            get() = commentInput.isNotBlank()
    }

    sealed interface CommentIntent: BaseContract.Intent {
        data object OnBackIconClick: CommentIntent
        data class OnCommentInputChanged(val commentInput: String): CommentIntent
        data object OnRegisterButtonClick: CommentIntent
        data object OnGuideButtonClick: CommentIntent
        data object OnMoreGuideClick: CommentIntent
        data object OnGuideXIconClick: CommentIntent
    }

    sealed interface CommentSideEffect: BaseContract.SideEffect {
        data object NavigateToBack: CommentSideEffect
        data object NavigateToHome: CommentSideEffect
    }
}