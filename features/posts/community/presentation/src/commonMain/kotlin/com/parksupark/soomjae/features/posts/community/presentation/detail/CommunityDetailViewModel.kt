package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.usecase.GetCommunityPostDetailWithLikedStream
import com.parksupark.soomjae.features.posts.community.presentation.models.toDetailUi
import kotlinx.collections.immutable.toImmutableList
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
import kotlinx.coroutines.launch

class CommunityDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPostWithLikedStream: GetCommunityPostDetailWithLikedStream,
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
) : ViewModel() {
    val postId: Long = savedStateHandle["postId"] ?: error("Post ID is missing")

    private val _uiStateFlow: MutableStateFlow<CommunityDetailState> = MutableStateFlow(
        if (postId != null) {
            CommunityDetailState.InitialLoading(postId)
        } else {
            CommunityDetailState.Error(UiText.DynamicString("Post ID is missing"))
        },
    )
    internal val uiStateFlow: StateFlow<CommunityDetailState> = _uiStateFlow.onStart {
        if (postId != null) {
            fetchPostDetails(postId)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CommunityDetailState.InitialLoading(postId ?: -1),
    )

    private val _eventChannel = Channel<CommunityDetailEvent>()
    internal val eventChannel = _eventChannel.receiveAsFlow()

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
        val state = _uiStateFlow.value
        if (state !is CommunityDetailState.Success) return

        val postDetail = state.postDetail

        viewModelScope.launch {
            _uiStateFlow.update { state -> if (state !is CommunityDetailState.Success) state else state.copy(isLikeLoading = true) }

            if (postDetail.isLiked) {
                likeRepository.unlike(postDetail.post.id)
            } else {
                likeRepository.like(postDetail.post.id)
            }

            _uiStateFlow.update { state -> if (state !is CommunityDetailState.Success) state else state.copy(isLikeLoading = false) }
        }
    }

    fun submitComment() {
        val state = _uiStateFlow.value
        if (state !is CommunityDetailState.Success) return

        viewModelScope.launch {
            _uiStateFlow.update { state ->
                if (state is CommunityDetailState.Success) {
                    state.copy(isCommentSubmitting = true)
                } else {
                    state
                }
            }

            commentRepository.addComment(postId = postId, content = state.inputCommentState.text.toString()).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = {
                    _uiStateFlow.update { state ->
                        if (state is CommunityDetailState.Success) {
                            state.copy(
                                postDetail = state.postDetail.copy(
                                    comments = (listOf(it.toUi()) + state.postDetail.comments).toImmutableList(),
                                ),
                                isCommentSubmitting = false,
                                inputCommentState = TextFieldState(),
                            )
                        } else {
                            state
                        }
                    }
                },
            )
        }
    }
}
