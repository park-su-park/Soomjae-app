package com.parksupark.soomjae.features.posts.common.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.common.domain.models.UpdateMeetingPost
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository

class UpdateMeetingPostUseCase(
    private val sessionRepository: SessionRepository,
    private val postRepository: MeetingPostRepository,
) {
    suspend operator fun invoke(updatedPost: UpdateMeetingPost): Either<DataFailure, NewPost> {
        if (!sessionRepository.isLoggedIn()) {
            return Either.Left(DataFailure.Validation.UNAUTHORIZED)
        }

        return postRepository.updatePost(updatedPost)
    }
}
