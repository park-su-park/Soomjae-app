package com.parksupark.soomjae.core.presentation.ui.controllers

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SoomjaeEventController {
    private val _eventChannel = Channel<SoomjaeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    suspend fun sendEvent(event: SoomjaeEvent) {
        _eventChannel.send(event)
    }
}

sealed interface SoomjaeEvent {
    data object LoginRequest : SoomjaeEvent
}
