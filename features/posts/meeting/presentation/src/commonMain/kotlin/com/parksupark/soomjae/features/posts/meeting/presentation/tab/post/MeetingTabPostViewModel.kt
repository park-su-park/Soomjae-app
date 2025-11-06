package com.parksupark.soomjae.features.posts.meeting.presentation.tab.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabEvent
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
class MeetingTabPostViewModel(
    private val meetingRepository: MeetingPostRepository,
    private val sessionRepository: SessionRepository,
    private val likeRepository: LikeRepository,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabPostState> =
        MutableStateFlow(MeetingTabPostState())
    val stateFlow: StateFlow<MeetingTabPostState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingTabEvent>()
    internal val events = eventChannel.receiveAsFlow()

    internal val posts: Flow<PagingData<MeetingPostUi>> = meetingRepository.getPostsStream()
        .map { pagingData ->
            pagingData.map { it.toMeetingPostUi() }
        }.cachedIn(viewModelScope)

    fun handleWritePostClick() {
        viewModelScope.launch {
            if (sessionRepository.isLoggedIn()) {
                eventChannel.send(MeetingTabEvent.NavigateToMeetingWrite)
            } else {
                soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
            }
        }
    }

    fun handlePostLikeClick(postId: Long) {
        // TODO: implement logic to like the post by optimistic updating
    }

    fun refreshPost() {
        viewModelScope.launch {
            eventChannel.send(MeetingTabEvent.RefreshPost)
        }
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _stateFlow.update { state ->
            state.copy(
                isPostsRefreshing = isRefreshing,
            )
        }
    }
}
