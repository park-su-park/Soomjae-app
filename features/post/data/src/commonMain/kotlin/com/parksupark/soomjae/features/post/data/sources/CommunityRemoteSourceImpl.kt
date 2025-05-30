package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostResponse
import io.ktor.client.HttpClient
import kotlinx.collections.immutable.ImmutableList

internal class CommunityRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CommunityRemoteSource {
    override suspend fun getPosts(page: Int): Either<DataFailure.Network, ImmutableList<CommunityPostResponse>> = httpClient.get(
        route = "/v1/boards/community/posts/list",
        queryParameters = mapOf("page" to page),
    )
}
