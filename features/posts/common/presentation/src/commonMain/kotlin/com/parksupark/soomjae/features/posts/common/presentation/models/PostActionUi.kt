package com.parksupark.soomjae.features.posts.common.presentation.models

data class PostActionUi(
    val type: PostActionType,
    val count: Long? = null,
    val isSelected: Boolean = false,
    val isEnabled: Boolean = true,
    val onClick: (() -> Unit)? = null,
)
