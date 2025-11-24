package com.parksupark.soomjae.core.presentation.ui.post.model

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

data class PostActionUi(
    val type: PostActionType,
    val count: Long? = null,
    val isSelected: Boolean = false,
    val isEnabled: Boolean = true,
    val onClick: (() -> Unit)? = null,
)
