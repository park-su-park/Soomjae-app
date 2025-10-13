package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display_unlimited
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
data class MeetingCreationUi(
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
        val participantCount = inputMaxParticipantCount.text.toString().toIntOrNull() ?: 0

        val participantCountString = if (participantCount == 0) {
            Res.string.meeting_create_participant_count_display_unlimited.value
        } else {
            Res.string.meeting_create_participant_count_display.value(participantCount)
        }

        return "$startDateTime - $endDateTime, $participantCountString"
    }

    companion object {

        val Empty: MeetingCreationUi
            get() = run {
                val dateTime = Clock.System.now().toLocalDateTime(currentSystemDefault())
                MeetingCreationUi(
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
    DateTimeUnit.MILLISECOND -> {
        // eliminate nanoseconds by converting to milliseconds and back
        LocalTime(hour, minute, second, nanosecond / NANOSECONDS_IN_MILLISECOND * NANOSECONDS_IN_MILLISECOND)
    }

    else -> this
}

private const val NANOSECONDS_IN_MILLISECOND = 1_000_000
