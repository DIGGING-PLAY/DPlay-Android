package com.example.record

import androidx.lifecycle.viewModelScope
import com.example.domain.model.DailyQuestion
import com.example.domain.model.QuestionError
import com.example.domain.repository.QuestionRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
@Inject
constructor(
    private val questionRepository: QuestionRepository,
) : BaseViewModel<RecordContract.RecordState, RecordContract.RecordIntent, RecordContract.RecordSideEffect>(
    RecordContract.RecordState(),
) {
    init {
        val now = YearMonth.now()
        loadQuestions(year = now.year, month = now.month.value)
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

    private fun loadQuestions(year: Int, month: Int) {
        viewModelScope.launch {
            questionRepository
                .getQuestionRecord(year = year, month = month).onSuccess { questions ->
                    updateState { copy(questionList = questions.toImmutableList()) }
                }.onFailure { e ->
                    when (e) {
                        is QuestionError.NotFound -> {
                            updateState { copy(questionList = persistentListOf()) }
                        }

                        else -> {
                            Timber.e(e, "error = $e")
                            updateState { copy(questionList = persistentListOf()) }
                        }
                    }
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
        loadQuestions(year = year, month = month)
        updateState { copy(year = year, month = month) }
    }
}
