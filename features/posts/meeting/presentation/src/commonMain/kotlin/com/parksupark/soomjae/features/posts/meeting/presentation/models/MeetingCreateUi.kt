package com.parksupark.soomjae.features.posts.meeting.presentation.models

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.utils.truncateTo

@OptIn(ExperimentalTime::class)
data class MeetingCreateUi(
    val starDate: LocalDate,
    val starTime: LocalTime,
    val endDate: LocalDate?,
    val endTime: LocalTime?,
    val maxParticipantCount: Int?,
) {

    companion object {

        val Empty: MeetingCreateUi
            get() = run {
                val dateTime = Clock.System.now().toLocalDateTime(currentSystemDefault())
                MeetingCreateUi(
                    starDate = dateTime.date,
                    starTime = dateTime.time.truncateTo(DateTimeUnit.MINUTE),
                    endDate = null,
                    endTime = null,
                    maxParticipantCount = null,
                )
            }
    }
}
