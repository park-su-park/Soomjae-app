package com.parksupark.soomjae.features.profile.data.source.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.put
import com.parksupark.soomjae.features.profile.data.model.dto.request.IntroductionPostRequest
import com.parksupark.soomjae.features.profile.data.model.dto.response.IntroductionPostResponse
import io.ktor.client.HttpClient

internal class IntroductionPostRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun getByMember(memberId: Long): Either<DataFailure.Network, IntroductionPostResponse> =
        httpClient.get(
            route = "/v1/members/introduction-post/by-memberId/$memberId",
        )

    suspend fun put(html: String): Either<DataFailure.Network, IntroductionPostResponse> =
        httpClient.put(
            route = "/v1/members/introduction-post",
            body = IntroductionPostRequest(html = html),
        )

    suspend fun deleteByMember(memberId: Long): Either<DataFailure.Network, Unit> =
        httpClient.delete(
            route = "/v1/members/introduction-post/by-memberId/$memberId",
        )
}
