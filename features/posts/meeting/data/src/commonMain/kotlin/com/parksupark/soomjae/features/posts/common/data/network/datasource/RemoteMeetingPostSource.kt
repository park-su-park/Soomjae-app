package com.parksupark.soomjae.features.posts.common.data.network.datasource

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.core.remote.networking.put
import com.parksupark.soomjae.features.posts.common.data.dto.request.PostMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostDetailResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostsResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.PostMeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.network.dto.PutMeetingPostRequest
import io.ktor.client.HttpClient

internal class RemoteMeetingPostSource(
    private val httpClient: HttpClient,
) {
    suspend fun createPost(
        request: PostMeetingPostRequest,
    ): Either<DataFailure.Network, PostMeetingPostResponse> = httpClient.post(
        route = "/v1/boards/meeting/posts",
        body = request,
    )

    suspend fun fetchPosts(
        page: Int,
        categoryIds: List<Long> = emptyList(),
        locationCodes: List<Long> = emptyList(),
        recruitment: Boolean? = null,
    ): Either<DataFailure.Network, MeetingPostsResponse> {
        val queryParams = mutableMapOf<String, Any>("page" to page).apply {
            if (categoryIds.isNotEmpty()) {
                put("categoryIds", categoryIds)
            }
            if (locationCodes.isNotEmpty()) {
                put("locationCodes", locationCodes)
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

    suspend fun fetchPostsByMemberId(
        page: Int,
        memberId: Long,
    ): Either<DataFailure.Network, MeetingPostsResponse> = httpClient.get(
        route = "/v1/members/$memberId/activities/posts/meeting",
        queryParameters = mapOf("page" to page),
    )

    suspend fun putPost(
        postId: Long,
        requestDto: PutMeetingPostRequest,
    ): Either<DataFailure, Long> = httpClient.put<PutMeetingPostRequest, Long>(
        route = "/v1/boards/meeting/posts/$postId",
        body = requestDto,
    )

    suspend fun deletePost(postId: Long): Either<DataFailure, Unit> = httpClient.delete(
        route = "/v1/boards/meeting/posts/$postId",
    )

    suspend fun fetchPostDetail(
        postId: Long,
    ): Either<DataFailure.Network, MeetingPostDetailResponse> = httpClient.get(
        route = "/v1/boards/meeting/posts/$postId",
    )
}
