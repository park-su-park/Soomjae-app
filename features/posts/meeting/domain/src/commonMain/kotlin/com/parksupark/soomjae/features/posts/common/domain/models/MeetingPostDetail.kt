package com.parksupark.soomjae.features.posts.common.domain.models

data class MeetingPostDetail(
    val post: MeetingPost,
    val comments: List<Comment>,
)
