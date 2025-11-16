package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.Comment

interface CommentRepository {
    suspend fun addComment(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment>

    suspend fun getComments(postId: Long): Either<DataFailure, List<Comment>>

    suspend fun deleteComment(
        postId: Long,
        commentID: Long,
    ): Either<DataFailure, Unit>
}
