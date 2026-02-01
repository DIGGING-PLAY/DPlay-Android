package com.example.record

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.DailyQuestion
import com.example.domain.model.FeedItem
import com.example.domain.model.QuestionError
import com.example.domain.repository.PostRepository
import com.example.domain.repository.QuestionRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.YearMonth
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RecordViewModel
    @Inject
    constructor(
        private val questionRepository: QuestionRepository,
        private val postRepository: PostRepository,
    ) : BaseViewModel<RecordContract.RecordState, RecordContract.RecordIntent, RecordContract.RecordSideEffect>(
            RecordContract.RecordState(),
        ) {
        private val selectedQuestionId = MutableStateFlow<Long?>(null)

        val questionPosts: Flow<PagingData<FeedItem>> =
            selectedQuestionId
                .flatMapLatest { questionId ->
                    if (questionId != null) {
                        postRepository.getPostsByQuestionId(
                            questionId = questionId,
                            onTotalCountFetched = { totalCount ->
                                updateState { copy(recordListTotalCount = totalCount) }
                            },
                            onLockedFetched = { locked ->
                                updateState { copy(locked = locked) }
                            },
                        )
                    } else {
                        flowOf(PagingData.empty())
                    }
                }.cachedIn(viewModelScope)

        init {
            val now = YearMonth.now()
            setDate(year = now.year, month = now.month.value)
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

                is RecordContract.RecordIntent.ChangeTooltipVisible -> {
                    updateState { copy(tooltipVisible = intent.isVisible) }
                }
            }
        }

        private fun loadQuestions(
            year: Int,
            month: Int,
        ) {
            viewModelScope.launch {
                questionRepository
                    .getQuestionRecord(year = year, month = month)
                    .onSuccess { questions ->
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
            selectedQuestionId.value = question?.questionId
            updateState { copy(selectedQuestion = question, recordListTotalCount = 0) }
        }

        private fun setDate(
            year: Int,
            month: Int,
        ) {
            loadQuestions(year = year, month = month)
            updateState { copy(year = year, month = month) }
        }
    }
