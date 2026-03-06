package com.example.dummy

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.DummyRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel
    @Inject
    constructor(
        private val dummyRepository: DummyRepository,
    ) : BaseViewModel<DummyContract.DummyState, DummyContract.DummyIntent, DummyContract.DummySideEffect>(
            DummyContract.DummyState(),
        ) {
        override fun handleIntent(intent: DummyContract.DummyIntent) {
            when (intent) {
                DummyContract.DummyIntent.Initialize -> {
                    viewModelScope.launch {
                        dummyRepository.getDummy(dummyId = 1L).onSuccess { dummy -> }.onFailure {}
                    }
                }
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
