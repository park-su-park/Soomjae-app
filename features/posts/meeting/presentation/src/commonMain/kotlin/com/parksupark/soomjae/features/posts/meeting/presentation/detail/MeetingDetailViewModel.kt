package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.toMeetingPostDetailUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MeetingDetailViewModel(
    private val meetingPostRepository: MeetingPostRepository,
    private val postId: Long,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingDetailState> = MutableStateFlow(MeetingDetailState.Loading)
    val stateFlow: StateFlow<MeetingDetailState> = _stateFlow.onStart {
        fetchMeetingPostDetails()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _stateFlow.value,
    )

    private fun fetchMeetingPostDetails() {
        viewModelScope.launch {
            meetingPostRepository.getMeetingPostDetail(postId).fold(
                ifLeft = { },
                ifRight = { postDetail ->
                    _stateFlow.update {
                        MeetingDetailState.Success(postDetail.toMeetingPostDetailUi())
                    }
                },
            )
        }
    }

    fun toggleLike() {
        // TODO: Implement like toggle functionality
    }
}
