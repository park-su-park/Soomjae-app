package com.parksupark.soomjae.features.profile.data.source.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.profile.data.model.dto.response.FetchProfileResponse
import io.ktor.client.HttpClient

internal class ProfileRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun fetchProfile(memberId: Long): Either<DataFailure.Network, FetchProfileResponse> {
        return httpClient.get<FetchProfileResponse>(
            route = "/v1/member/${memberId}/profiles"
        )
    }
}
