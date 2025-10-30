package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import com.parksupark.soomjae.features.posts.community.presentation.models.toDetailUi
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityDestination
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: SoomjaeDispatcher,
    private val postRepository: CommunityPostRepository,
    private val sessionRepository: SessionRepository,
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    val postId = savedStateHandle.toRoute<CommunityDestination.CommunityDetail>().postID

    private val _uiStateFlow: MutableStateFlow<CommunityDetailState> = MutableStateFlow(
        CommunityDetailState.InitialLoading(postId),
    )
    val uiStateFlow: StateFlow<CommunityDetailState> = _uiStateFlow.onStart {
        fetchPostDetails(postId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CommunityDetailState.InitialLoading(postId),
    )

    private val _eventChannel = Channel<CommunityDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun fetchPostDetails(postId: Long) {
        viewModelScope.launch(dispatcher.io) {
            postRepository.getPostDetails(postId).fold(
                ifLeft = { },
                ifRight = { postDetail ->
                    _uiStateFlow.update {
                        CommunityDetailState.Success(
                            postDetail = postDetail.toDetailUi(),
                            isMine = sessionRepository.getCurrentUserId().toString() ==
                                postDetail.post.author.id,
                            inputCommentState = TextFieldState(),
                        )
                    }
                },
            )
        }
    }

    fun deletePost() {
        viewModelScope.launch(dispatcher.io) {
            _uiStateFlow.update { state ->
                (state as? CommunityDetailState.Success)?.copy(isDeleteLoading = true) ?: state
            }

            postRepository.deletePost(postId).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = {
                    _eventChannel.send(CommunityDetailEvent.PostDeleted)
                },
            )

            _uiStateFlow.update { state ->
                (state as? CommunityDetailState.Success)?.copy(isDeleteLoading = false) ?: state
            }
        }
    }

    fun toggleLike() {
        val state = _uiStateFlow.value
        if (state !is CommunityDetailState.Success) return

        val postDetail = state.postDetail
        val newLikedState = !postDetail.isLiked

        viewModelScope.launch {
            _uiStateFlow.update {
                (it as? CommunityDetailState.Success)?.copy(
                    postDetail = it.postDetail.copy(isLiked = newLikedState),
                    isLikeLoading = true,
                ) ?: it
            }

            val result = if (newLikedState) {
                likeRepository.like(postDetail.post.id)
            } else {
                likeRepository.unlike(postDetail.post.id)
            }

            result.onLeft {
                _uiStateFlow.update { state ->
                    (state as? CommunityDetailState.Success)?.copy(
                        postDetail = state.postDetail.copy(isLiked = !newLikedState),
                    ) ?: state
                }
            }

            _uiStateFlow.update {
                (it as? CommunityDetailState.Success)?.copy(isLikeLoading = false) ?: it
            }
        }
    }

    fun handleCommentFieldClick() {
        viewModelScope.launch(dispatcher.io) {
            if (!sessionRepository.isLoggedIn()) {
                soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
            }
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

            commentRepository.addComment(
                postId = postId,
                content = state.inputCommentState.text.toString(),
            ).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = {
                    _uiStateFlow.update { state ->
                        if (state is CommunityDetailState.Success) {
                            state.copy(
                                postDetail = state.postDetail.copy(
                                    comments = (
                                        listOf(
                                            it.toUi(),
                                        ) + state.postDetail.comments
                                    ).toImmutableList(),
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
