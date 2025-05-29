package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommunityTabViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CommunityTabState> = MutableStateFlow(CommunityTabState())
    val stateFlow: StateFlow<CommunityTabState> = _stateFlow.asStateFlow()
}
