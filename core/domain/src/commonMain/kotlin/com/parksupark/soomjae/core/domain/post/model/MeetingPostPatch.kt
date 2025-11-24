package com.parksupark.soomjae.core.domain.post.model

interface MeetingPostPatch {
    data class Updated(val post: MeetingPost) : MeetingPostPatch

    data class LikeChanged(val isLiked: Boolean) : MeetingPostPatch

    data object Deleted : MeetingPostPatch
}
