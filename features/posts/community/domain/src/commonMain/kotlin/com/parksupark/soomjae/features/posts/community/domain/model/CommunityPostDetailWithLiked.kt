package com.parksupark.soomjae.features.posts.community.domain.model

data class CommunityPostDetailWithLiked(
    val postDetail: CommunityPostDetail,
    val isLiked: Boolean,
)
