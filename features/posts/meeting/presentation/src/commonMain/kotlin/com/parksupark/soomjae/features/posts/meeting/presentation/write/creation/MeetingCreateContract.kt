package com.parksupark.soomjae.features.posts.meeting.presentation.write.creation

import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreationUi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class MeetingCreationState(
    val meeting: MeetingCreationUi = MeetingCreationUi.Empty,
)

internal sealed interface MeetingCreationAction {
    data object OnBackClick : MeetingCreationAction

    data object OnCreateClick : MeetingCreationAction

    data class OnStartDateSelected(val startDate: LocalDate) : MeetingCreationAction

    data class OnStartTimeSelected(val startTime: LocalTime) : MeetingCreationAction

    data class OnEndDateSelected(val endDate: LocalDate) : MeetingCreationAction

    data class OnEndTimeSelected(val endTime: LocalTime) : MeetingCreationAction
}
