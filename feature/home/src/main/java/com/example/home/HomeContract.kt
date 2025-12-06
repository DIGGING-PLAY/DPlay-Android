package com.example.home

import com.example.ui.base.BaseContract

class HomeContract {
    data class HomeState(
        val loading: Boolean = false,
        val count: Int = 0,
    ) : BaseContract.State

    sealed interface HomeIntent : BaseContract.Intent {
        data object Initialize : HomeIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : HomeIntent
    }

    sealed interface HomeSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : HomeSideEffect
    }
}
