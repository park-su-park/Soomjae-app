package com.parksupark.soomjae.features.posts.community.data.remote.source

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.core.remote.networking.put
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostDetailResponse
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostResponse
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostsResponse
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CreateCommunityPostRequest
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CreateCommunityPostResponse
import io.ktor.client.HttpClient

internal class CommunityRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CommunityRemoteSource {
    override suspend fun getPosts(
        page: Int,
    ): Either<DataFailure.Network, List<CommunityPostResponse>> =
        httpClient.get<CommunityPostsResponse>(
            route = "/v1/boards/community/posts/list",
            queryParameters = mapOf("page" to page),
        ).map { response ->
            response.posts
        }

    override suspend fun postPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
    ): Either<DataFailure.Network, CreateCommunityPostResponse> =
        httpClient.post<CreateCommunityPostRequest, CreateCommunityPostResponse>(
            route = "/v1/boards/community/posts",
            body = CreateCommunityPostRequest(
                title = title,
                content = content,
                categoryId = categoryId,
                locationCode = locationCode,
            ),
        )

    override suspend fun getPostDetails(
        postId: Long,
    ): Either<DataFailure.Network, CommunityPostDetailResponse> = httpClient.get(
        route = "/v1/boards/community/posts/$postId",
    )

    override suspend fun putPost(
        postId: Long,
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
    ): Either<DataFailure.Network, Long> = httpClient.put<CreateCommunityPostRequest, Long>(
        route = "/v1/boards/community/posts/$postId",
        body = CreateCommunityPostRequest(
            title = title,
            content = content,
            categoryId = categoryId,
            locationCode = locationCode,
        ),
    )

    override suspend fun deletePost(postId: Long): Either<DataFailure.Network, Unit> =
        httpClient.delete<Unit>(
            route = "/v1/boards/community/posts/$postId",
        )
}
