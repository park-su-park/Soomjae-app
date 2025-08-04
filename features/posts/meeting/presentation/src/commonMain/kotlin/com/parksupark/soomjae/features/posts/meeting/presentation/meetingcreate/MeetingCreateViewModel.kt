package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingCreateViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MeetingCreateState> = MutableStateFlow(MeetingCreateState())

    internal val stateFlow: StateFlow<MeetingCreateState> = _stateFlow.asStateFlow()
}
