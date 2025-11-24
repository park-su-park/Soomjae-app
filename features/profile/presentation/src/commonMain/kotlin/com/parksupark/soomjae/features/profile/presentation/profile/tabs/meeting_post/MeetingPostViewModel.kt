package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.InjectedParam

class MeetingPostViewModel(
    @InjectedParam memberId: Long,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingPostState> =
        MutableStateFlow(MeetingPostState())
    val stateFlow: StateFlow<MeetingPostState> = _stateFlow.asStateFlow()

    fun refreshPosts() {
        // TODO:
    }
}
