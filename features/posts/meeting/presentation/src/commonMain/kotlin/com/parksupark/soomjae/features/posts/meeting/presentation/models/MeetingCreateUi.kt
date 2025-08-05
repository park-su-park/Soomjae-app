package com.parksupark.soomjae.features.posts.meeting.presentation.models

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
    val maxParticipantCount: Int?,
) {

    companion object {

        val Empty: MeetingCreateUi
            get() = run {
                val dateTime = Clock.System.now().toLocalDateTime(currentSystemDefault())
                MeetingCreateUi(
                    startDate = dateTime.date,
                    startTime = dateTime.time.truncateTo(DateTimeUnit.MINUTE),
                    endDate = null,
                    endTime = null,
                    maxParticipantCount = null,
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
