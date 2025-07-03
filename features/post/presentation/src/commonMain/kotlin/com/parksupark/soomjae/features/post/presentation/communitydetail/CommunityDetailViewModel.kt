package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.post.domain.usecases.GetCommunityPostDetailWithLikedStream
import com.parksupark.soomjae.features.post.presentation.models.toDetailUi
import com.parksupark.soomjae.features.post.presentation.navigation.PostDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class CommunityDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPostWithLikedStream: GetCommunityPostDetailWithLikedStream,

) : ViewModel() {
    val postId: Long? = savedStateHandle[PostDestination.CommunityDetail::postId.name]

    private val _uiStateFlow: MutableStateFlow<CommunityDetailState> = MutableStateFlow(
        if (postId != null) {
            CommunityDetailState.InitialLoading(postId)
        } else {
            CommunityDetailState.Error(UiText.DynamicString("Post ID is missing"))
        },
    )
    val uiStateFlow: StateFlow<CommunityDetailState> = _uiStateFlow.onStart {
        if (postId != null) {
            fetchPostDetails(postId)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CommunityDetailState.InitialLoading(postId ?: -1),
    )

    private val _eventChannel = Channel<CommunityDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun fetchPostDetails(postId: Long) {
        getPostWithLikedStream(postId).onEach { result ->
            result.fold(
                ifLeft = { error ->
                    _uiStateFlow.update {
                        CommunityDetailState.Error(error.asUiText())
                    }
                },
                ifRight = { post ->
                    _uiStateFlow.update {
                        CommunityDetailState.Success(
                            postDetail = post.toDetailUi(),
                        )
                    }
                },
            )
        }.launchIn(viewModelScope)
    }

    fun toggleLike() {
        // Todo
    }
}
