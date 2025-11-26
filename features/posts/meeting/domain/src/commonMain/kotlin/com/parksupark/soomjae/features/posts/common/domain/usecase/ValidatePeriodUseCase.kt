@file:OptIn(ExperimentalTime::class)

package com.parksupark.soomjae.features.posts.common.domain.usecase

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class ValidatePeriodUseCase(
    private val minDuration: Duration = Duration.ZERO,
) {

    enum class ChangedField { START, END }

    data class Input(
        val startDate: LocalDate,
        val startTime: LocalTime,
        val endDate: LocalDate,
        val endTime: LocalTime,
        val changed: ChangedField,
    )

    data class Result(
        val startDate: LocalDate,
        val startTime: LocalTime,
        val endDate: LocalDate,
        val endTime: LocalTime,
        val isValid: Boolean,
    )

    operator fun invoke(input: Input): Result {
        val startDateTime = LocalDateTime.of(input.startDate, input.startTime)
        val endDateTime = LocalDateTime.of(input.endDate, input.endTime)

        val isValid = startDateTime <= endDateTime

        if (isValid) {
            return Result(
                startDate = input.startDate,
                startTime = input.startTime,
                endDate = input.endDate,
                endTime = input.endTime,
                isValid = true,
            )
        }

        return when (input.changed) {
            ChangedField.START -> {
                val adjustedEnd = startDateTime.plus(minDuration)
                Result(
                    startDate = input.startDate,
                    startTime = input.startTime,
                    endDate = adjustedEnd.date,
                    endTime = adjustedEnd.time,
                    isValid = false,
                )
            }

            ChangedField.END -> {
                val adjustedStart = endDateTime.minus(minDuration)
                Result(
                    startDate = adjustedStart.date,
                    startTime = adjustedStart.time,
                    endDate = input.endDate,
                    endTime = input.endTime,
                    isValid = false,
                )
            }
        }
    }
}

private fun LocalDateTime.Companion.of(
    date: LocalDate,
    time: LocalTime,
) = date.atTime(time)

private fun LocalDateTime.plus(duration: Duration): LocalDateTime {
    val tempTimeZone = TimeZone.UTC
    val instant = this.toInstant(tempTimeZone) + duration
    return instant.toLocalDateTime(tempTimeZone)
}

private fun LocalDateTime.minus(duration: Duration): LocalDateTime {
    val tempTimeZone = TimeZone.UTC
    val instant = this.toInstant(tempTimeZone) - duration
    return instant.toLocalDateTime(tempTimeZone)
}
