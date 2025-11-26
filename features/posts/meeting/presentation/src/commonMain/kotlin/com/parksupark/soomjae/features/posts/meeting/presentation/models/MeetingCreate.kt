package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class MeetingCreate(
    val startDate: LocalDate,
    val startTime: LocalTime,
    val endDate: LocalDate?,
    val endTime: LocalTime?,
    val inputMaxParticipantCount: Int,
)

internal fun MeetingCreate.toMeetingCreateUi(): MeetingCreationUi = MeetingCreationUi(
    startDate = startDate,
    startTime = startTime,
    endDate = endDate,
    endTime = endTime,
    inputMaxParticipantCount = TextFieldState(inputMaxParticipantCount.toString()),
)

internal fun MeetingCreationUi.toMeetingCreate(): MeetingCreate = MeetingCreate(
    startDate = startDate,
    startTime = startTime,
    endDate = endDate,
    endTime = endTime,
    inputMaxParticipantCount = inputMaxParticipantCount.text.toString().toIntOrNull() ?: 0,
)
