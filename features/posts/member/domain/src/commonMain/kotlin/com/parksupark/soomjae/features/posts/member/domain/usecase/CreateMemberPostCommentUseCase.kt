package com.parksupark.soomjae.features.posts.member.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository

class CreateMemberPostCommentUseCase(
    private val sessionRepository: SessionRepository,
    private val commentRepository: CommentRepository,
) {
    suspend operator fun invoke(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment> {
        if (!sessionRepository.isLoggedIn()) {
            return Either.Left(DataFailure.Validation.UNAUTHORIZED)
        }

        return commentRepository.addComment(
            postId = postId,
            content = content,
        )
    }
}
