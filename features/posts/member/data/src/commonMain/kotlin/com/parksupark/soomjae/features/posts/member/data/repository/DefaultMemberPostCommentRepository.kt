package com.parksupark.soomjae.features.posts.member.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository

internal class DefaultMemberPostCommentRepository : CommentRepository {
    override suspend fun addComment(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment> {
        // TODO: ("Not yet implemented")
        return Either.Left(DataFailure.Local.UNKNOWN)
    }

    override suspend fun deleteComment(
        postId: Long,
        commentID: Long,
    ): Either<DataFailure, Unit> {
        // TODO: ("Not yet implemented")
        return Either.Left(DataFailure.Local.UNKNOWN)
    }

    override suspend fun getComments(postId: Long): Either<DataFailure, List<Comment>> {
        // TODO: ("Not yet implemented")
        return Either.Left(DataFailure.Local.UNKNOWN)
    }
}
