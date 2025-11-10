package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Immutable
data class MeetingFormUi(
    val participantLimit: TextFieldState = TextFieldState(),
    val hasParticipantLimit: Boolean = true,

    val period: DateTimeRangeUi = DateTimeRangeUi.Empty,
)
