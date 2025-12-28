package com.example.record

import com.example.ui.base.BaseContract

class RecordContract {
    data class RecordState(
        val loading: Boolean = false,
        val count: Int = 0,
    ) : BaseContract.State

    sealed interface RecordIntent : BaseContract.Intent {
        data object Initialize : RecordIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : RecordIntent
    }

    sealed interface RecordSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : RecordSideEffect
    }
}
