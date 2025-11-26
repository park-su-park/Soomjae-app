package com.parksupark.soomjae.features.posts.member.data.source.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.dtos.AddCommentRequest
import com.parksupark.soomjae.features.posts.common.data.dtos.AddCommentResponse
import io.ktor.client.HttpClient

internal class RemoteMemberPostCommentDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun createComment(
        postId: Long,
        content: String,
    ): Either<DataFailure.Network, AddCommentResponse> =
        httpClient.post<AddCommentRequest, AddCommentResponse>(
            route = "/v1/boards/member/posts/$postId/comments",
            body = AddCommentRequest(content = content),
        )
}
