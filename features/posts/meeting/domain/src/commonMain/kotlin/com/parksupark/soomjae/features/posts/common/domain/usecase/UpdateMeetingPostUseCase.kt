package com.parksupark.soomjae.features.posts.common.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.NewPost
import com.parksupark.soomjae.core.domain.post.model.UpdateMeetingPost
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository

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
