package com.parksupark.soomjae.features.posts.meeting.presentation.models

import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DateTimeRangeUi(
    val startDate: LocalDate,
    val startTime: LocalTime,
    val endDate: LocalDate = startDate,
    val endTime: LocalTime = startTime,
    val isAllDay: Boolean,
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        val Empty: DateTimeRangeUi
            get() {
                val timeZone = TimeZone.currentSystemDefault()

                val nowInstant = Clock.System.now()
                val now = nowInstant.toLocalDateTime(timeZone)

                val tempEndInstant = nowInstant.plus(1.hours)
                val tempEnd = tempEndInstant.toLocalDateTime(timeZone)
                return DateTimeRangeUi(
                    startDate = now.date,
                    startTime = now.time,
                    endDate = tempEnd.date,
                    endTime = tempEnd.time,
                    isAllDay = false,
                )
            }
    }
}
