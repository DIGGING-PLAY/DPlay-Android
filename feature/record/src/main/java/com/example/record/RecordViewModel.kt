package com.example.record

import com.example.common.model.DailyQuestion
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
    @Inject
    constructor() : BaseViewModel<RecordContract.RecordState, RecordContract.RecordIntent, RecordContract.RecordSideEffect>(
            RecordContract.RecordState(),
        ) {
        init {
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
                )
            }
        }

        override fun handleIntent(intent: RecordContract.RecordIntent) {
            when (intent) {
                RecordContract.RecordIntent.Initialize -> {}
                is RecordContract.RecordIntent.OnQuestionClick -> setQuestion(question = intent.question)
                is RecordContract.RecordIntent.OnListBackButtonClick -> setQuestion(question = null)
            }
        }

        private fun setQuestion(question: DailyQuestion?) {
            updateState { copy(selectedQuestion = question) }
        }
    }
