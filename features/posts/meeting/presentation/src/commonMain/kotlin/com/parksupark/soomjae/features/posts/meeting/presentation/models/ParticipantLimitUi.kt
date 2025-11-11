package com.parksupark.soomjae.features.posts.meeting.presentation.models

import androidx.compose.foundation.text.input.TextFieldState

data class ParticipantLimitUi(
    val isLimited: Boolean = true,
    val limitCount: TextFieldState = TextFieldState(),
) {
    val isUnlimited: Boolean
        get() = !isLimited
}
