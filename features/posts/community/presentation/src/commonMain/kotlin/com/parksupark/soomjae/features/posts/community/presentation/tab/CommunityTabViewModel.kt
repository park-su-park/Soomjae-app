package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import com.parksupark.soomjae.features.posts.community.presentation.models.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityTabViewModel(
    repository: CommunityPostRepository,
    private val sessionRepository: SessionRepository,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CommunityTabState> =
        MutableStateFlow(CommunityTabState())
    internal val stateFlow: StateFlow<CommunityTabState> = _stateFlow.asStateFlow()

    private val _eventChannel = Channel<CommunityTabEvent>()
    internal val eventChannel = _eventChannel.receiveAsFlow()

    init {
        repository.getAllPosts()
            .cachedIn(viewModelScope)
            .map { it.map { post -> post.toUi() } }
            .onEach { posts -> _stateFlow.update { it.copy(posts = posts) } }
            .launchIn(viewModelScope)
    }

    fun handleCommunityWriteClick() {
        viewModelScope.launch {
            if (sessionRepository.isLoggedIn()) {
                _eventChannel.send(CommunityTabEvent.NavigateToCommunityWrite)
            } else {
                soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
            }
        }
    }

    fun refreshPosts() = viewModelScope.launch {
        _eventChannel.send(CommunityTabEvent.RefreshPosts)
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _stateFlow.update { it.copy(isPostsRefreshing = isRefreshing) }
    }
}
