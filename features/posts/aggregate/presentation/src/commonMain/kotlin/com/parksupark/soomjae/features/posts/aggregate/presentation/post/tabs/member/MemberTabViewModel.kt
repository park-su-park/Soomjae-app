package com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MemberTabViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MemberTabState> = MutableStateFlow(MemberTabState())
    val stateFlow: StateFlow<MemberTabState> = _stateFlow.asStateFlow()
}
