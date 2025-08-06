package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_additional_info_unlimited
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
data class MeetingCreateUi(
    val startDate: LocalDate,
    val startTime: LocalTime,
    val endDate: LocalDate?,
    val endTime: LocalTime?,
    val inputMaxParticipantCount: TextFieldState,
) {

    @Composable
    fun toDisplayString(): String {
        val startDateTime = "$startDate $startTime"
        val endDateTime = if (endDate != null && endTime != null) {
            "$endDate $endTime"
        } else {
            ""
        }
        val participantCount = if (inputMaxParticipantCount.text.isEmpty()) {
            Res.string.meeting_create_participant_count_additional_info_unlimited.value
        } else {
            Res.string.meeting_create_participant_count_display.value(inputMaxParticipantCount.text)
        }

        return "$startDateTime - $endDateTime, $participantCount"
    }

    companion object {

        val Empty: MeetingCreateUi
            get() = run {
                val dateTime = Clock.System.now().toLocalDateTime(currentSystemDefault())
                MeetingCreateUi(
                    startDate = dateTime.date,
                    startTime = dateTime.time.truncateTo(DateTimeUnit.MINUTE),
                    endDate = null,
                    endTime = null,
                    inputMaxParticipantCount = TextFieldState("0"),
                )
            }
    }
}

private fun LocalTime.truncateTo(timeUnit: DateTimeUnit.TimeBased): LocalTime = when (timeUnit) {
    DateTimeUnit.HOUR -> LocalTime(hour, 0, 0)
    DateTimeUnit.MINUTE -> LocalTime(hour, minute, 0)
    DateTimeUnit.SECOND -> LocalTime(hour, minute, second)
    DateTimeUnit.MILLISECOND -> LocalTime(hour, minute, second, nanosecond / 1_000_000)
    else -> this
}
