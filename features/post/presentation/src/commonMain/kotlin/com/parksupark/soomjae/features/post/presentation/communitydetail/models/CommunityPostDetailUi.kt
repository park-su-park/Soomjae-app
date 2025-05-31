package com.parksupark.soomjae.features.post.presentation.communitydetail.models

import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

internal data class CommunityPostDetailUi(
    val post: CommunityPostUi,
    val isLiked: Boolean,
    val likeCount: Long,
    val commentCount: Long,
)
