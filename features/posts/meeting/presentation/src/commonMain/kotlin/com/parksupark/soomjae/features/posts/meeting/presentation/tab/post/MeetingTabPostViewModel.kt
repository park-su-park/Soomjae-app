package com.parksupark.soomjae.features.posts.meeting.presentation.tab.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.features.posts.common.domain.event.MeetingPostEventBus
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingTabFilterOption
import com.parksupark.soomjae.features.posts.meeting.presentation.models.toDomain
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
class MeetingTabPostViewModel(
    private val meetingRepository: MeetingPostRepository,
    private val sessionRepository: SessionRepository,
    private val bus: MeetingPostEventBus,
    private val soomjaeEventController: SoomjaeEventController,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabPostState> =
        MutableStateFlow(MeetingTabPostState())
    val stateFlow: StateFlow<MeetingTabPostState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingTabPostEvent>()
    internal val events = eventChannel.receiveAsFlow()

    private val filterState: MutableStateFlow<MeetingTabFilterOption> =
        MutableStateFlow(MeetingTabFilterOption())

    private val _createdCache = MutableStateFlow<ImmutableList<MeetingPostUi>>(persistentListOf())
    val createdCache = _createdCache.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val posts: Flow<PagingData<MeetingPostUi>> = filterState.flatMapLatest { option ->
        meetingRepository.getPostsStream(filter = option.toDomain())
    }.map { pagingData ->
        pagingData.map { post -> post.toMeetingPostUi() }
    }.cachedIn(viewModelScope)

    init {
        observeCreatedEvent()
    }

    private fun observeCreatedEvent() {
        bus.created.onEach { post ->
            _createdCache.update { currentList ->
                (listOf(post.toMeetingPostUi()) + currentList).toPersistentList()
            }

            eventChannel.send(MeetingTabPostEvent.PostCreated)
        }.launchIn(viewModelScope)
    }

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

        if (!isRefreshing) {
            _createdCache.update { persistentListOf() }
        }
    }

    fun updateFilterOption(option: MeetingTabFilterOption) {
        filterState.update { option }
    }
}
