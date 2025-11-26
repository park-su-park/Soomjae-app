package com.parksupark.soomjae.core.domain.post.model

data class MeetingPostDetail(
    val post: MeetingPost,

    val maxParticipationCount: Int,
    val currentParticipantCount: Int,
    val isUserJoined: Boolean,
    val recruitmentPeriod: RecruitmentPeriod,

    val comments: List<Comment>,
)
