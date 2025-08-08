package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingDetailState> = MutableStateFlow(MeetingDetailState.Loading)
    val stateFlow: StateFlow<MeetingDetailState> = _stateFlow.asStateFlow()

    fun toggleLike() {
        TODO("Not yet implemented")
    }
}
