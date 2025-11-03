package com.example.dummy

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DummyViewModel
        @Inject constructor() : BaseViewModel<DummyContract.DummyState, DummyContract.DummyIntent, DummyContract.DummySideEffect>(
    DummyContract.DummyState()
) {
    override fun handleIntent(intent: DummyContract.DummyIntent) {
                when (intent) {
            DummyContract.DummyIntent.Initialize -> {}
            is DummyContract.DummyIntent.OnClickNumberButton -> increment(intent.number)
        }
    }

        private fun increment(count: Int) {
                updateState {
            copy(count = currentState.count + count)
        }
        setSideEffect(DummyContract.DummySideEffect.ShowSnackBar(count.toString()))
    }
}