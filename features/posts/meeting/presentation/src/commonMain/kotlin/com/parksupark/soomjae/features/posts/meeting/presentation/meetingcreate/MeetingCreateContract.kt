package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreateUi
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalTime::class)
internal data class MeetingCreateState(
    val meeting: MeetingCreateUi = MeetingCreateUi.Empty,
)

internal sealed interface MeetingCreateAction {
    data object OnBackClick : MeetingCreateAction

    data object OnCreateClick : MeetingCreateAction

    data class OnStartDateSelected(val startDate: LocalDate) : MeetingCreateAction

    data class OnEndDateSelected(val endDate: LocalDate) : MeetingCreateAction
}
