package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MeetingDetailState> = MutableStateFlow(MeetingDetailState())

    val stateFlow: StateFlow<MeetingDetailState> = _stateFlow.asStateFlow()
}
