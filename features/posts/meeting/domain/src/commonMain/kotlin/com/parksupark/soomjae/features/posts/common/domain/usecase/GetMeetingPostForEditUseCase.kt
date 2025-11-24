package com.parksupark.soomjae.features.posts.common.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.MeetingPostDetail
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingForEdit
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingRecruitment

class GetMeetingPostForEditUseCase(
    private val meetingPostRepository: MeetingPostRepository,
) {
    suspend operator fun invoke(postId: Long): Either<DataFailure, MeetingForEdit> =
        meetingPostRepository.getPostDetail(postId).map { detail ->
            detail.toMeetingForEdit()
        }
}

private fun MeetingPostDetail.toMeetingForEdit(): MeetingForEdit {
    val post = this.post
    return MeetingForEdit(
        id = post.id,
        title = post.title,
        content = post.content,
        category = post.category,
        location = post.location,
        recruitment = MeetingRecruitment(
            maxParticipationCount = if (post.maxParticipationCount == -1) {
                null
            } else {
                post.maxParticipationCount
            },
            recruitmentPeriod = this.recruitmentPeriod,
        ),
    )
}
