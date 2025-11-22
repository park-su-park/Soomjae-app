package com.parksupark.soomjae.features.posts.common.domain.models

interface MeetingPostPatch {
    data class Created(val post: MeetingPost) : MeetingPostPatch

    data class Updated(val post: MeetingPost) : MeetingPostPatch

    data class LikeChanged(val isLiked: Boolean) : MeetingPostPatch

    data object Deleted : MeetingPostPatch
}
