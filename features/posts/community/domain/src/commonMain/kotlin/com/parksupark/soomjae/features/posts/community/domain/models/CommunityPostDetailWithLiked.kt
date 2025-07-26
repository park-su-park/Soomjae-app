package com.parksupark.soomjae.features.posts.community.domain.models

data class CommunityPostDetailWithLiked(
    val post: CommunityPostDetail,
    val isLiked: Boolean,
)
