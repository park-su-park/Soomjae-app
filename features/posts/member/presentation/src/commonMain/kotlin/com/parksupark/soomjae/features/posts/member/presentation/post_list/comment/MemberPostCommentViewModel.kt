package com.parksupark.soomjae.features.posts.member.presentation.post_list.comment

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.member.domain.usecase.CreateMemberPostCommentUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemberPostCommentViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val soomjaeEventController: SoomjaeEventController,
    private val commentRepository: CommentRepository,
    private val createCommentUseCase: CreateMemberPostCommentUseCase,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MemberPostCommentState> =
        MutableStateFlow(MemberPostCommentState())
    val stateFlow: StateFlow<MemberPostCommentState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MemberPostCommentEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    init {
        _stateFlow.map { it.postId }
            .distinctUntilChanged()
            .onEach { postId ->
                if (postId != null) {
                    loadCommentsForPost(postId)
                } else {
                    clearComments()
                }
            }
            .launchIn(viewModelScope)
    }

    fun createComment() {
        val current = _stateFlow.value

        val postId = current.postId ?: return
        if (current.isCommentSubmitting) return

        val content = current.inputComments.text.trim().toString()
        if (content.isEmpty()) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isCommentSubmitting = true) }

            val result = withContext(dispatcher.io) {
                createCommentUseCase(postId, content)
            }

            result.fold(
                ifLeft = {
                    if (it == DataFailure.Validation.UNAUTHORIZED) {
                        soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
                    } else {
                        eventChannel.send(
                            MemberPostCommentEvent.Error(message = it.asUiText()),
                        )
                    }
                },
                ifRight = { newComment ->
                    _stateFlow.update {
                        it.inputComments.clearText()
                        it.copy(
                            comments = prepend(newComment.toUi(), it.comments).toPersistentList(),
                        )
                    }

                    eventChannel.send(MemberPostCommentEvent.CommentSubmissionSuccess)
                },
            )

            _stateFlow.update { it.copy(isCommentSubmitting = false) }
        }
    }

    fun updatePostId(postId: Long?) {
        _stateFlow.update {
            it.copy(postId = postId)
        }
    }

    private suspend fun loadCommentsForPost(postId: Long) {
        if (stateFlow.value.isLoading) return

        _stateFlow.update { it.copy(isLoading = true) }

        val result = withContext(dispatcher.io) {
            commentRepository.getComments(postId)
        }

        result.fold(
            ifLeft = {
                eventChannel.send(
                    MemberPostCommentEvent.Error(message = it.asUiText()),
                )
            },
            ifRight = { comments ->
                _stateFlow.update { state ->
                    state.copy(
                        comments = comments.map { it.toUi() }.toImmutableList(),
                    )
                }
            },
        )

        _stateFlow.update { it.copy(isLoading = false) }
    }

    private fun clearComments() {
        _stateFlow.update {
            it.copy(comments = persistentListOf())
        }
    }
}

private fun <E> prepend(
    first: E,
    rest: List<E>,
): List<E> = listOf(first) + rest
