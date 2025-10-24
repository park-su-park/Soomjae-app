package com.parksupark.soomjae.features.profile.data.datasources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.profile.data.dto.response.GetProfileMemberPostResponse
import com.parksupark.soomjae.features.profile.data.dto.response.toProfileMemberPost
import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import io.ktor.client.HttpClient

internal class ProfileMemberPostRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun fetchPosts(
        memberId: Long,
        page: Int,
    ): Either<DataFailure.Network, List<ProfileMemberPost>> =
        httpClient.get<List<GetProfileMemberPostResponse>>(
            route = "/v1/boards/member/posts/$memberId/grid",
            queryParameters = mapOf("page" to page),
        ).map { response ->
            response.map { postResponse -> postResponse.toProfileMemberPost() }
        }
}
