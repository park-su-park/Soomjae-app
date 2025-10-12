package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MeetingWriteScreenViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingWriteScreenState> = MutableStateFlow(MeetingWriteScreenState())
    val stateFlow: StateFlow<MeetingWriteScreenState> = _stateFlow.asStateFlow()

    fun setScreenState(compose: MeetingPostWriteScreenState) {
        _stateFlow.update {
            it.copy(compose)
        }
    }
}
