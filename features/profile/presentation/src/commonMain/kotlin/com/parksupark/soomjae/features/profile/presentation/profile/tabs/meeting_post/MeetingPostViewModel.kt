package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.core.presentation.ui.post.model.toMeetingPostUi
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.InjectedParam

class MeetingPostViewModel(
    @InjectedParam memberId: Long,

    private val postRepository: MeetingPostRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingPostState> =
        MutableStateFlow(MeetingPostState())
    val stateFlow: StateFlow<MeetingPostState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingPostEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    @OptIn(ExperimentalTime::class)
    val posts = postRepository.getByMemberId(memberId).map { paging ->
        paging.map {
            it.toMeetingPostUi()
        }
    }.cachedIn(viewModelScope)

    fun refreshPosts() {
        viewModelScope.launch {
            eventChannel.send(MeetingPostEvent.RefreshPost)
        }
    }

    fun setRefresh(refreshing: Boolean) {
        _stateFlow.update { state ->
            state.copy(
                isRefreshing = refreshing,
            )
        }
    }
}
