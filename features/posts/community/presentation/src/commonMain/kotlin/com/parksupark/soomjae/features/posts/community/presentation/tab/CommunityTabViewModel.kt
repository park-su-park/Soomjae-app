package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import app.cash.paging.PagingData
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostPatch
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.models.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityTabViewModel(
    postRepository: CommunityPostRepository,
    private val sessionRepository: SessionRepository,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CommunityTabState> =
        MutableStateFlow(CommunityTabState())
    internal val stateFlow: StateFlow<CommunityTabState> = _stateFlow.asStateFlow()

    private val _eventChannel = Channel<CommunityTabEvent>()
    internal val eventChannel = _eventChannel.receiveAsFlow()

    val posts: Flow<PagingData<CommunityPostUi>> = postRepository.getAllPosts()
        .cachedIn(viewModelScope)
        .combine(postRepository.postPatches) { pagingData, patches ->
            pagingData.filter { post ->
                patches[post.id] !is CommunityPostPatch.Deleted
            }.map { post ->
                when (val patch = patches[post.id]) {
                    is CommunityPostPatch.Updated -> patch.post.toUi()
                    is CommunityPostPatch.LikeChanged -> post.toUi(isUserLiked = patch.isLiked)
                    else -> post.toUi()
                }
            }
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
