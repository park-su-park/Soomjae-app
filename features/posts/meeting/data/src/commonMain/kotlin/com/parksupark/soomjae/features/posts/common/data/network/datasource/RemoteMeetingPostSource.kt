package com.parksupark.soomjae.features.posts.common.data.network.datasource

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostsResponse
import io.ktor.client.HttpClient

internal class RemoteMeetingPostSource(
    private val httpClient: HttpClient,
) {
    suspend fun getMeetingPosts(
        page: Int,
        categoryIds: List<Long> = emptyList(),
        locationCodes: List<Long> = emptyList(),
        recruitment: Boolean? = null,
    ): Either<DataFailure.Network, MeetingPostsResponse> {
        val queryParams = mutableMapOf<String, Any>("page" to page).apply {
            if (categoryIds.isNotEmpty()) {
                put("categoryIds", categoryIds.joinToString(","))
            }
            if (locationCodes.isNotEmpty()) {
                put("locationCodes", locationCodes.joinToString(","))
            }
            recruitment?.let {
                put("recruitment", it)
            }
        }

        return httpClient.get<MeetingPostsResponse>(
            route = "/v1/boards/meeting/posts/list",
            queryParameters = queryParams,
        )
    }
}
