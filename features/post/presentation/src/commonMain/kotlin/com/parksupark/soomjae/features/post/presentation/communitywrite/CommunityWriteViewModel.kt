package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommunityWriteViewModel : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<CommunityWriteState> = MutableStateFlow(CommunityWriteState())
    val uiStateFlow: StateFlow<CommunityWriteState> = _uiStateFlow.asStateFlow()
}
