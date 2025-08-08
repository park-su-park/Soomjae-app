package com.parksupark.soomjae.features.posts.meeting.presentation.detail

class MeetingDetailState

sealed interface MeetingDetailAction {
    data object OnBackClick : MeetingDetailAction
}
