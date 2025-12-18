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
        override fun handleIntent(intent: DetailContract.DetailIntent) {
            when (intent) {
                DetailContract.DetailIntent.Initialize -> {}
                is DetailContract.DetailIntent.OnClickNumberButton -> increment(intent.number)
            }
        }

        private fun increment(count: Int) {
            setSideEffect(DetailContract.DetailSideEffect.ShowSnackBar(count.toString()))
        }
    }
