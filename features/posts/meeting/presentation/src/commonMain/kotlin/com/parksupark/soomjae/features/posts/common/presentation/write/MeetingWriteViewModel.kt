package com.parksupark.soomjae.features.posts.common.presentation.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingWriteViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MeetingWriteState> = MutableStateFlow(MeetingWriteState())

    internal val stateFlow: StateFlow<MeetingWriteState> = _stateFlow.asStateFlow()
}
