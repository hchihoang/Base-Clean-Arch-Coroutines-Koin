package com.inetkr.base.utils.helper

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ObjectEventChannel {
    private val _eventFlow = MutableSharedFlow<Any>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun sendEvent(event: Any) {
        _eventFlow.emit(event)
    }
}