package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

internal class MeetingCreateState

internal sealed interface MeetingCreateAction {
    data object OnBackClick : MeetingCreateAction
}
