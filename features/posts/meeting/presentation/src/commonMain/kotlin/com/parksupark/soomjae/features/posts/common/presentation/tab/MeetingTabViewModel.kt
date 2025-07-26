package com.parksupark.soomjae.features.posts.common.presentation.tab

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingTabViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabState> = MutableStateFlow(MeetingTabState())
    val stateFlow: StateFlow<MeetingTabState> = _stateFlow.asStateFlow()
}
