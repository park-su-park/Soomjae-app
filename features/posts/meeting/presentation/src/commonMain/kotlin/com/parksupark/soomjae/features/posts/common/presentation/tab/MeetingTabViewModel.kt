package com.parksupark.soomjae.features.posts.common.presentation.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.common.presentation.tab.models.toMeetingPostUi
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalTime::class)
class MeetingTabViewModel(
    private val meetingRepository: MeetingPostRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingTabState> = MutableStateFlow(MeetingTabState())
    internal val stateFlow: StateFlow<MeetingTabState> = _stateFlow.asStateFlow()

    internal val posts: Flow<PagingData<MeetingPostUi>> = meetingRepository.getPostsStream()
        .map { pagingData ->
            pagingData.map { it.toMeetingPostUi() }
        }.cachedIn(viewModelScope)
}
