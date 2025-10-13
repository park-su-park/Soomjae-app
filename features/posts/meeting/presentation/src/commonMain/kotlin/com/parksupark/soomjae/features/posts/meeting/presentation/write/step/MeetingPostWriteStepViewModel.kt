package com.parksupark.soomjae.features.posts.meeting.presentation.write.step

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MeetingPostWriteStepViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingPostWriteStepState> =
        MutableStateFlow(MeetingPostWriteStepState())
    val stateFlow: StateFlow<MeetingPostWriteStepState> = _stateFlow.asStateFlow()

    fun setScreenState(compose: MeetingPostWriteStep) {
        _stateFlow.update {
            it.copy(compose)
        }
    }
}
