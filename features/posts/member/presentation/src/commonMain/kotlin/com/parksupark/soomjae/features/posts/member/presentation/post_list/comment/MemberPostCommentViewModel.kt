package com.parksupark.soomjae.features.posts.member.presentation.post_list.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.common.utils.fold
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberPostCommentViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val commentRepository: CommentRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MemberPostCommentState> =
        MutableStateFlow(MemberPostCommentState())
    val stateFlow: StateFlow<MemberPostCommentState> = _stateFlow.asStateFlow()

    fun loadCommentsForPost(postId: Long) {
        if (stateFlow.value.isLoading) return

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { it.copy(isLoading = true) }

            commentRepository.getComments(postId).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = { comments ->
                    _stateFlow.update { state ->
                        state.copy(
                            comments = comments.map { it.toUi() }.toImmutableList(),
                        )
                    }
                },
                finally = {
                    _stateFlow.update { it.copy(isLoading = false) }
                },
            )
        }
    }

    fun clearComments() {
        _stateFlow.update {
            it.copy(
                isLoading = false,
                comments = persistentListOf(),
            )
        }
    }
}
