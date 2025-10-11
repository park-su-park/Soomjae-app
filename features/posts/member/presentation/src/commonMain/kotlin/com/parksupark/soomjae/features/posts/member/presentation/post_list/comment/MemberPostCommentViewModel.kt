package com.parksupark.soomjae.features.posts.member.presentation.post_list.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
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
    val dispatcher: SoomjaeDispatcher,
    val commentRepository: CommentRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MemberPostCommentState> = MutableStateFlow(MemberPostCommentState())
    val stateFlow: StateFlow<MemberPostCommentState> = _stateFlow.asStateFlow()

    fun loadCommentsForPost(postId: Long) {
        if (stateFlow.value.isLoading) return

        viewModelScope.launch(dispatcher.io) {
            commentRepository.getComments(postId).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = { comments ->
                    _stateFlow.update { state ->
                        state.copy(
                            isLoading = false,
                            comments = comments.map { it.toUi() }.toImmutableList(),
                        )
                    }
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
