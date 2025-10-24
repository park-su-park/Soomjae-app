package com.parksupark.soomjae.features.posts.community.domain.model

data class CommunityPostWithLiked(
    val post: CommunityPost,
    val isLiked: Boolean,
)
