package com.example.dummy

import com.example.ui.base.BaseContract

class DummyContract {
    data class DummyState(
        val loading: Boolean = false,
        val count: Int = 0,
    ) : BaseContract.State

    sealed interface DummyIntent : BaseContract.Intent {
        data object Initialize : DummyIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : DummyIntent
    }

    sealed interface DummySideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : DummySideEffect
    }
}
