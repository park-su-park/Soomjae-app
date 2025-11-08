package com.parksupark.soomjae.features.posts.meeting.presentation.tab.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingTabFilterOption
import com.parksupark.soomjae.features.posts.meeting.presentation.models.toDomain
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
class MeetingTabPostViewModel(
    private val meetingRepository: MeetingPostRepository,
    private val sessionRepository: SessionRepository,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabPostState> =
        MutableStateFlow(MeetingTabPostState())
    val stateFlow: StateFlow<MeetingTabPostState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingTabPostEvent>()
    internal val events = eventChannel.receiveAsFlow()

    private val filterState: MutableStateFlow<MeetingTabFilterOption> =
        MutableStateFlow(MeetingTabFilterOption())

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val posts: Flow<PagingData<MeetingPostUi>> = filterState.flatMapLatest { option ->
        meetingRepository.getPatchedPostsStream(filter = option.toDomain())
    }.map { pagingData ->
        pagingData.map { post -> post.toMeetingPostUi() }
    }.cachedIn(viewModelScope)

    fun handleWritePostClick() {
        viewModelScope.launch {
            if (sessionRepository.isLoggedIn()) {
                eventChannel.send(MeetingTabPostEvent.NavigateToMeetingWrite)
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
            eventChannel.send(MeetingTabPostEvent.RefreshPost)
        }
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _stateFlow.update { state ->
            state.copy(
                isPostsRefreshing = isRefreshing,
            )
        }
    }

    fun updateFilterOption(option: MeetingTabFilterOption) {
        filterState.update { option }
    }
}
