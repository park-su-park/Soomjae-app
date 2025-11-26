package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingForEdit
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
@Immutable
data class MeetingFormUi(
    val period: DateTimeRangeUi = DateTimeRangeUi.Empty,
    val participantLimit: ParticipantLimitUi = ParticipantLimitUi(),
)

@OptIn(ExperimentalTime::class)
fun fromEditMeeting(
    editMeeting: MeetingForEdit,
    timeZone: TimeZone,
): MeetingFormUi {
    val period = editMeeting.recruitment.recruitmentPeriod
    val startDateTime = period.startTime.toLocalDateTime(timeZone)
    val endDateTime = period.endTime.toLocalDateTime(timeZone)
    val periodUi = DateTimeRangeUi(
        startDate = startDateTime.date,
        startTime = startDateTime.time,
        endDate = endDateTime.date,
        endTime = endDateTime.time,
        isAllDay = false,
    )

    val participant = editMeeting.recruitment.maxParticipationCount
    val participantLimitUi = ParticipantLimitUi(
        isLimited = participant != null,
        limitCount = TextFieldState(initialText = participant?.toString() ?: ""),
    )
    return MeetingFormUi(
        period = periodUi,
        participantLimit = participantLimitUi,
    )
}
