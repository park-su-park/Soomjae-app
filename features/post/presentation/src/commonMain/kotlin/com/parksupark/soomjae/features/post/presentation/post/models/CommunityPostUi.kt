package com.parksupark.soomjae.features.post.presentation.post.models

import com.parksupark.soomjae.features.post.domain.models.CommunityPost

internal data class CommunityPostUi(
    val id: String,
    val title: String,
)

internal fun CommunityPost.toUi(): CommunityPostUi = CommunityPostUi(
    id = id,
    title = id,
)
