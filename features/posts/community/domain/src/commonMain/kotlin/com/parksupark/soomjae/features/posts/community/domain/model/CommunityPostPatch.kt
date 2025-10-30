package com.parksupark.soomjae.features.posts.community.domain.model

sealed interface CommunityPostPatch {
    data class Updated(val post: CommunityPost) : CommunityPostPatch

    data object Deleted : CommunityPostPatch

    data class LikeChanged(val isLiked: Boolean) : CommunityPostPatch
}
