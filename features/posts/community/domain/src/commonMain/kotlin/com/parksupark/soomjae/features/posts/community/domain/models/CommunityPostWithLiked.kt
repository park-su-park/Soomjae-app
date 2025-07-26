package com.parksupark.soomjae.features.posts.community.domain.models

data class CommunityPostWithLiked(
    val post: CommunityPost,
    val isLiked: Boolean,
)
