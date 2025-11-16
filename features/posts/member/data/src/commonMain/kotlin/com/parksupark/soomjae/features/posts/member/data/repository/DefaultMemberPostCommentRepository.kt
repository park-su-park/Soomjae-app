package com.parksupark.soomjae.features.posts.member.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toComment
import com.parksupark.soomjae.features.posts.common.data.dtos.toComment
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostCommentDataSource
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostDataSource

internal class DefaultMemberPostCommentRepository(
    private val postRemoteSource: RemoteMemberPostDataSource,
    private val commentRemoteSource: RemoteMemberPostCommentDataSource,
) : CommentRepository {
    override suspend fun addComment(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment> = commentRemoteSource.createComment(
        postId = postId,
        content = content,
    ).map { response ->
        response.toComment()
    }

    override suspend fun getComments(postId: Long): Either<DataFailure, List<Comment>> =
        postRemoteSource.getPostDetail(postId).map { response ->
            response.comments.map {
                it.toComment()
            }
        }

    override suspend fun deleteComment(
        postId: Long,
        commentID: Long,
    ): Either<DataFailure, Unit> {
        // TODO: ("Not yet implemented")
        return Either.Left(DataFailure.Local.UNKNOWN)
    }
}
