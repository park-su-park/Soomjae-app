package com.parksupark.soomjae.features.posts.community.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.dtos.AddCommentResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toComment
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import io.ktor.client.HttpClient

internal class CommunityCommentRepository(
    private val httpClient: HttpClient,
) : CommentRepository {
    override suspend fun addComment(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment> = httpClient.get<AddCommentResponse>(
        route = "/v1/boards/community/posts/$postId/comments",
    ).map { response ->
        response.toComment()
    }

    override suspend fun deleteComment(
        postId: Long,
        commentID: Long,
    ): Either<DataFailure, Unit> = httpClient.delete<Unit>(
        route = "/v1/boards/community/posts/$postId/comments/$commentID",
    )

    override suspend fun getComments(postId: Long): Either<DataFailure, List<Comment>> {
        // TODO: Implement this method
        return Either.Left(DataFailure.Local.UNKNOWN)
    }
}
