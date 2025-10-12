package com.parksupark.soomjae.features.posts.community.domain.models

data class CommunityPostDetailWithLiked(
    val postDetail: CommunityPostDetail,
    val isLiked: Boolean,
)
