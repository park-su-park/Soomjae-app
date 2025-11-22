package com.parksupark.soomjae.features.posts.common.domain.models

import com.parksupark.soomjae.core.domain.models.Member
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class MeetingPost(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val author: Member,
    val category: Category?,
    val location: Location?,
    val likeCount: Int,
    val isUserLiked: Boolean,
    val commentCount: Int,
    val maxParticipationCount: Int,
    val currentParticipantCount: Int,
    val recruitmentPeriod: RecruitmentPeriod,
) {
    companion object {
        fun createNew(
            id: Long,
            title: String,
            content: String,
            maxParticipationCount: Int,
            period: RecruitmentPeriod,
        ): MeetingPost {
            return MeetingPost(
                id = id,
                title = title,
                content = content,
                createdAt = Clock.System.now(),
                author = Member(
                    id = "나",
                    nickname = "나",
                    profileImageUrl = "",
                ),
                category = null,
                location = null,
                likeCount = 0,
                isUserLiked = false,
                commentCount = 0,
                maxParticipationCount = maxParticipationCount,
                currentParticipantCount = 0,
                recruitmentPeriod = period
            )
        }
    }
}
