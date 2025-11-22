package com.parksupark.soomjae.features.posts.meeting.presentation.tab.models

import androidx.compose.runtime.Immutable

@Immutable
data class ParticipantUi(
    val max: Int? = 0,
    val current: Int = 0,
) {
    val isLimitless: Boolean
        get() = max == null

    val isFull: Boolean
        get() = !isLimitless && current >= (max ?: 0)
}
