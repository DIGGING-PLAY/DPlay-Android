package com.example.home

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor() : BaseViewModel<HomeContract.HomeState, HomeContract.HomeIntent, HomeContract.HomeSideEffect>(
            HomeContract.HomeState(),
        ) {
        override fun handleIntent(intent: HomeContract.HomeIntent) {
            when (intent) {
                HomeContract.HomeIntent.Initialize -> {}
                is HomeContract.HomeIntent.OnClickNumberButton -> increment(intent.number)
            }
        }

        private fun increment(count: Int) {
            updateState {
                copy(count = currentState.count + count)
            }
            setSideEffect(HomeContract.HomeSideEffect.ShowSnackBar(count.toString()))
        }
    }
