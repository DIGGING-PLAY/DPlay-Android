package com.example.common.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRefreshTrigger
    @Inject
    constructor() {
        private val _refreshEvent = MutableSharedFlow<Unit>()
        val refreshEvent = _refreshEvent.asSharedFlow()

        suspend fun refresh() = _refreshEvent.emit(Unit)
    }
