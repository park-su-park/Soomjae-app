package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.toMemberPostUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberPostListViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val soomjaeEventController: SoomjaeEventController,
    private val sessionRepository: SessionRepository,
    private val memberPostRepository: MemberPostRepository,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MemberPostListState> =
        MutableStateFlow(MemberPostListState())
    val stateFlow: StateFlow<MemberPostListState> = _stateFlow.asStateFlow()

    val posts = memberPostRepository.getPostsStream()
        .map { pagingData ->
            pagingData.map { post -> post.toMemberPostUi() }
        }.cachedIn(viewModelScope)

    private val eventChannel = Channel<MemberPostListEvent>()
    val events = eventChannel.receiveAsFlow()

    fun refreshPosts() {
        viewModelScope.launch {
            eventChannel.send(MemberPostListEvent.RefreshPosts)
        }
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _stateFlow.update {
            it.copy(isPostsRefreshing = isRefreshing)
        }
    }

    fun handleWritePostClick() {
        viewModelScope.launch(dispatcher.io) {
            if (sessionRepository.isLoggedIn()) {
                eventChannel.send(MemberPostListEvent.NavigateToWritePost)
            } else {
                soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
            }
        }
    }

    fun setSelectedPostId(postId: Long?) {
        _stateFlow.update {
            it.copy(selectedPostId = postId)
        }
    }
}
