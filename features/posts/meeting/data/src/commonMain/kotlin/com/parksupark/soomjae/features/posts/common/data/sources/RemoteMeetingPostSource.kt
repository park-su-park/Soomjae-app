package com.parksupark.soomjae.features.posts.common.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.dtos.MeetingPostResponse
import io.ktor.client.HttpClient

internal class RemoteMeetingPostSource(
    private val httpClient: HttpClient,
) {
    suspend fun getMeetingPosts(page: Int): Either<DataFailure.Network, List<MeetingPostResponse>> =
        httpClient.get<List<MeetingPostResponse>>(
            route = "/v1/boards/community/posts/list",
            queryParameters = mapOf("page" to page.toString()),
        )
}
