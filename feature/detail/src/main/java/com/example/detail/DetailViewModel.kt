package com.example.detail

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor() : BaseViewModel<DetailContract.DetailState, DetailContract.DetailIntent, DetailContract.DetailSideEffect>(
            DetailContract.DetailState(),
        ) {
        init {
            loadData()
        }

        override fun handleIntent(intent: DetailContract.DetailIntent) {
            when (intent) {
                is DetailContract.DetailIntent.OnBackButtonClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateBackStack)
                }
                is DetailContract.DetailIntent.OnBookmarkClick -> toggleBookmark()
                is DetailContract.DetailIntent.OnDeleteClick -> deletePost()
                is DetailContract.DetailIntent.OnLikeClick -> toggleLike()
                is DetailContract.DetailIntent.OnMeatBallsClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.ShowBottomSheet)
                }
                is DetailContract.DetailIntent.OnReportClick -> reportPost()
                is DetailContract.DetailIntent.OnStreamClick -> streamTrack()
                is DetailContract.DetailIntent.OnWriterProfileClick -> {
                    setSideEffect(DetailContract.DetailSideEffect.NavigateToWriterProfile(writerUserId = intent.writerUserId))
                }
            }
        }

        private fun loadData() {
        }

        private fun toggleBookmark() {
        }

        private fun toggleLike() {
        }

        private fun deletePost() {
        }

        private fun reportPost() {
        }

        private fun streamTrack() {
        }
    }
