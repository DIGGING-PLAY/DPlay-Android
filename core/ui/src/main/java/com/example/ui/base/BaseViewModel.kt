package com.example.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : BaseContract.State, INTENT : BaseContract.Intent, SIDE_EFFECT : BaseContract.SideEffect>(
    initialState: STATE,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect: Channel<SIDE_EFFECT> = Channel(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    protected val currentState: STATE
        get() = _uiState.value

    abstract fun handleIntent(intent: INTENT)

    protected fun updateState(reducer: STATE.() -> STATE) {
        _uiState.update { currentState.reducer() }
    }

    protected fun setSideEffect(effect: SIDE_EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }
}
