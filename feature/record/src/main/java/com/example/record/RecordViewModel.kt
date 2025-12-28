package com.example.record

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
    @Inject
    constructor() : BaseViewModel<RecordContract.RecordState, RecordContract.RecordIntent, RecordContract.RecordSideEffect>(
            RecordContract.RecordState(),
        ) {
        override fun handleIntent(intent: RecordContract.RecordIntent) {
            when (intent) {
                RecordContract.RecordIntent.Initialize -> {}
                is RecordContract.RecordIntent.OnClickNumberButton -> increment(intent.number)
            }
        }

        private fun increment(count: Int) {
            updateState {
                copy(count = currentState.count + count)
            }
            setSideEffect(RecordContract.RecordSideEffect.ShowSnackBar(count.toString()))
        }
    }
