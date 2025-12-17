package com.example.detail

import com.example.ui.base.BaseContract

class DetailContract {
    data class DetailState(
        val loading: Boolean = false,
        val count: Int = 0,
    ) : BaseContract.State

    sealed interface DetailIntent : BaseContract.Intent {
        data object Initialize : DetailIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : DetailIntent
    }

    sealed interface DetailSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : DetailSideEffect
    }
}
