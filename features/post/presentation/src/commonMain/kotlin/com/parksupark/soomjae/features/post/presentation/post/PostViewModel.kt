package com.parksupark.soomjae.features.post.presentation.post

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostViewModel : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<PostState> = MutableStateFlow(PostState())
    val uiStateFlow: StateFlow<PostState> = _uiStateFlow.asStateFlow()
}
