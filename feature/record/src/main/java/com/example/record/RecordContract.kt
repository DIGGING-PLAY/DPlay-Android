package com.example.record

import com.example.common.model.DailyQuestion
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class RecordContract {
    data class RecordState(
        val loading: Boolean = false,
        val questionList: ImmutableList<DailyQuestion> = persistentListOf(),
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
