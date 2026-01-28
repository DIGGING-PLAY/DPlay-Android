package com.example.common.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

// 추후 Type-safe 하게 재사용할 수 있도록 리팩토링
@Singleton
class RegisteredTrackRefreshTrigger
    @Inject
    constructor() {
        private val _refreshEvent = MutableSharedFlow<Unit>()
        val refreshEvent = _refreshEvent.asSharedFlow()

        suspend fun refresh() = _refreshEvent.emit(Unit)
    }
