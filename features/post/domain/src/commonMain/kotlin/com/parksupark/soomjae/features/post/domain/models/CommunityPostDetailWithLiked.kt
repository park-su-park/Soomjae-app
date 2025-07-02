package com.parksupark.soomjae.features.post.domain.models

data class CommunityPostDetailWithLiked(
    val post: CommunityPostDetail,
    val isLiked: Boolean,
)
