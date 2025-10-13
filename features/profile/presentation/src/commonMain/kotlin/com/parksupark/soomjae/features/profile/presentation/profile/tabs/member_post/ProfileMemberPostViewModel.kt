package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileMemberPostViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ProfileMemberPostState> = MutableStateFlow(ProfileMemberPostState())

    val stateFlow: StateFlow<ProfileMemberPostState> = _stateFlow.asStateFlow()
}
