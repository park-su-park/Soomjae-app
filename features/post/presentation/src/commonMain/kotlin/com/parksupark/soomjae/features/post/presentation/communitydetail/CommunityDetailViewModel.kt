package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.post.presentation.navigation.PostDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class CommunityDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val postId: String? = savedStateHandle[PostDestination.CommunityDetail::postId.name]

    private val _uiStateFlow: MutableStateFlow<CommunityDetailState> = MutableStateFlow(
        if (postId != null) {
            CommunityDetailState.InitialLoading(postId)
        } else {
            CommunityDetailState.Error(UiText.DynamicString("Post ID is missing"))
        },
    )
    val uiStateFlow: StateFlow<CommunityDetailState> = _uiStateFlow.asStateFlow()

    private val _eventChannel = Channel<CommunityDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        uiStateFlow.distinctUntilChangedBy { it::class }
            .onEach { state ->
                if (state is CommunityDetailState.InitialLoading) {
                    // TODO: Fetch the post details using the postId
                }
            }.launchIn(viewModelScope)
    }
}
