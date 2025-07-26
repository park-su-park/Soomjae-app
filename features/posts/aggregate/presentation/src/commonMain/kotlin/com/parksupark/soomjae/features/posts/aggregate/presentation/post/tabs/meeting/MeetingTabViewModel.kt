package com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.meeting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingTabViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabState> = MutableStateFlow(MeetingTabState())
    val stateFlow: StateFlow<MeetingTabState> = _stateFlow.asStateFlow()
}
