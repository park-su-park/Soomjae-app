package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreateUi
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class MeetingCreateState {
    val meeting = MeetingCreateUi.Empty
}

internal sealed interface MeetingCreateAction {
    data object OnBackClick : MeetingCreateAction

    data object OnCreateClick : MeetingCreateAction
}
