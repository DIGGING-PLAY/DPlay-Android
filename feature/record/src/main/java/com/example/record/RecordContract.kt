package com.example.record

import com.example.domain.model.DailyQuestion
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class RecordContract {
    data class RecordState(
        val loading: Boolean = false,
        val questionList: ImmutableList<DailyQuestion> = persistentListOf(),
        val year: Int = 2026,
        val month: Int = 1,
        val selectedQuestion: DailyQuestion? = null,
        val datePickerBottomSheetVisible: Boolean = false,
        val recordListTotalCount: Int = 0,
        val tooltipVisible: Boolean = false,
        val locked: Boolean = true,
    ) : BaseContract.State

    sealed interface RecordIntent : BaseContract.Intent {
        data object Initialize : RecordIntent

        data class OnQuestionClick(
            val question: DailyQuestion,
        ) : RecordIntent

        data object OnListBackButtonClick : RecordIntent

        data class ChangeBottomSheetVisible(
            val isVisible: Boolean,
        ) : RecordIntent

        data class OnMusicClick(
            val postId: Long,
        ) : RecordIntent

        data class SelectDate(
            val year: Int,
            val month: Int,
        ) : RecordIntent

        data class ChangeTooltipVisible(
            val isVisible: Boolean,
        ) : RecordIntent
    }

    sealed interface RecordSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : RecordSideEffect

        data class NavigateToPostDetail(
            val postId: Long,
        ) : RecordSideEffect
    }
}
