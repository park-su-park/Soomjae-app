package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.runtime.Immutable
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Immutable
data class MeetingFormUi(
    val period: DateTimeRangeUi = DateTimeRangeUi.Empty,
    val participantLimit: ParticipantLimitUi = ParticipantLimitUi(),
)
