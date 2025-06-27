package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostResponse
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostsResponse
import com.parksupark.soomjae.features.post.data.dtos.PostCommunityPostRequest
import com.parksupark.soomjae.features.post.data.dtos.PostCommunityPostResponse
import io.ktor.client.HttpClient

internal class CommunityRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CommunityRemoteSource {
    override suspend fun getPosts(page: Int): Either<DataFailure.Network, List<CommunityPostResponse>> =
        httpClient.get<CommunityPostsResponse>(
            route = "/v1/boards/community/posts/list",
            queryParameters = mapOf("page" to page),
        ).map { response ->
            response.posts
        }

    override suspend fun postPost(
        title: String,
        content: String,
        categoryId: Long,
    ): Either<DataFailure.Network, PostCommunityPostResponse> = httpClient.post<PostCommunityPostRequest, PostCommunityPostResponse>(
        route = "/v1/boards/community/posts",
        body = PostCommunityPostRequest(
            title = title,
            content = content,
            categoryId = categoryId,
        ),
    )

    override suspend fun getPostDetails(postId: Long): Either<DataFailure.Network, CommunityPostResponse> = httpClient.get(
        route = "/v1/boards/community/posts/$postId",
    )
}
