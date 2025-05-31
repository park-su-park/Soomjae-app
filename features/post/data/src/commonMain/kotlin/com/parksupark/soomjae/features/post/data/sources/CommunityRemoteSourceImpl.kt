package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostResponse
import com.parksupark.soomjae.features.post.data.dtos.PostCommunityPostRequest
import com.parksupark.soomjae.features.post.data.dtos.PostCommunityPostResponse
import io.ktor.client.HttpClient
import kotlinx.collections.immutable.ImmutableList

internal class CommunityRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CommunityRemoteSource {
    override suspend fun getPosts(page: Int): Either<DataFailure.Network, ImmutableList<CommunityPostResponse>> = httpClient.get(
        route = "/v1/boards/community/posts/list",
        queryParameters = mapOf("page" to page),
    )

    override suspend fun postPost(
        title: String,
        content: String,
    ): Either<DataFailure.Network, PostCommunityPostResponse> = httpClient.post<PostCommunityPostRequest, PostCommunityPostResponse>(
        route = "/v1/boards/community/posts",
        body = PostCommunityPostRequest(
            title = title,
            content = content,
        ),
    )

    override suspend fun getPostDetails(postId: String): Either<DataFailure.Network, CommunityPostResponse> = httpClient.get(
        route = "/v1/boards/community/posts/$postId",
    )
}
