package com.parksupark.soomjae.features.posts.meeting.presentation.write.create

import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreateUi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class MeetingCreateState(
    val meeting: MeetingCreateUi = MeetingCreateUi.Empty,
)

internal sealed interface MeetingCreateAction {
    data object OnBackClick : MeetingCreateAction

    data object OnCreateClick : MeetingCreateAction

    data class OnStartDateSelected(val startDate: LocalDate) : MeetingCreateAction

    data class OnStartTimeSelected(val startTime: LocalTime) : MeetingCreateAction

    data class OnEndDateSelected(val endDate: LocalDate) : MeetingCreateAction

    data class OnEndTimeSelected(val endTime: LocalTime) : MeetingCreateAction
}
