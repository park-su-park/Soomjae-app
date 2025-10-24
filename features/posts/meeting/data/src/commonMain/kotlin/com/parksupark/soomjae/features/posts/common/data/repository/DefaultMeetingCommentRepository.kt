package com.parksupark.soomjae.features.posts.common.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.common.dtos.CommentResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toComment
import com.parksupark.soomjae.features.posts.common.data.dtos.AddCommentRequest
import com.parksupark.soomjae.features.posts.common.data.dtos.AddCommentResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toComment
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import io.ktor.client.HttpClient

internal class DefaultMeetingCommentRepository(
    private val httpClient: HttpClient,
) : CommentRepository {
    override suspend fun addComment(
        postId: Long,
        content: String,
    ): Either<DataFailure, Comment> = httpClient.post<AddCommentRequest, AddCommentResponse>(
        route = "/v1/boards/meeting/posts/$postId/comments",
        body = AddCommentRequest(content = content),
    ).map { response ->
        response.toComment()
    }

    override suspend fun deleteComment(
        postId: Long,
        commentID: Long,
    ): Either<DataFailure, Unit> = httpClient.delete<Unit>(
        route = "/v1/boards/meeting/posts/$postId/comments/$commentID",
    )

    override suspend fun getComments(postId: Long): Either<DataFailure, List<Comment>> =
        httpClient.get<List<CommentResponse>>(
            route = "/v1/boards/meeting/posts/$postId/comments",
        ).map { responses ->
            responses.map { it.toComment() }
        }
}
