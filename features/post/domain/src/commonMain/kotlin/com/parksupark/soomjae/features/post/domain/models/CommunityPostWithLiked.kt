package com.parksupark.soomjae.features.post.domain.models

data class CommunityPostWithLiked(
    val post: CommunityPost,
    val isLiked: Boolean,
)
