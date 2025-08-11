package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingPostDetailUi

sealed interface MeetingDetailState {
    data object Loading : MeetingDetailState

    data class Success(
        val postDetail: MeetingPostDetailUi,
    ) : MeetingDetailState
}

sealed interface MeetingDetailAction {
    data object OnBackClick : MeetingDetailAction

    data object OnToggleLikeClick : MeetingDetailAction
}
