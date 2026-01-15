package com.example.record

import com.example.domain.model.DailyQuestion
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
    @Inject
    constructor() : BaseViewModel<RecordContract.RecordState, RecordContract.RecordIntent, RecordContract.RecordSideEffect>(
            RecordContract.RecordState(),
        ) {
        init {
            val now = YearMonth.now()

            updateState {
                copy(
                    questionList =
                        persistentListOf(
                            DailyQuestion(
                                questionId = 0,
                                title = "여행갈 때 플레이리스트에 넣는 노래는?",
                                date = "2025-11-01",
                            ),
                            DailyQuestion(
                                questionId = 1,
                                title = "여행갈 때 플레이리스트에 넣는 노래는?",
                                date = "2025-11-04",
                            ),
                        ),
                    year = now.year,
                    month = now.monthValue,
                )
            }
        }

        override fun handleIntent(intent: RecordContract.RecordIntent) {
            when (intent) {
                is RecordContract.RecordIntent.Initialize -> {}
                is RecordContract.RecordIntent.OnQuestionClick -> setQuestion(question = intent.question)
                is RecordContract.RecordIntent.OnListBackButtonClick -> setQuestion(question = null)
                is RecordContract.RecordIntent.OnMusicClick -> {
                    setSideEffect(RecordContract.RecordSideEffect.NavigateToPostDetail(postId = intent.postId))
                }

                is RecordContract.RecordIntent.SelectDate -> {
                    setDate(year = intent.year, month = intent.month)
                }

                is RecordContract.RecordIntent.ChangeBottomSheetVisible -> {
                    updateState { copy(datePickerBottomSheetVisible = intent.isVisible) }
                }
            }
        }

        private fun setQuestion(question: DailyQuestion?) {
            updateState { copy(selectedQuestion = question) }
        }

        private fun setDate(
            year: Int,
            month: Int,
        ) {
            updateState { copy(year = year, month = month) }
        }
    }
