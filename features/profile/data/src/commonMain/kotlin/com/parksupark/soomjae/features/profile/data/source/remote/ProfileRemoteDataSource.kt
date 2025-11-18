package com.parksupark.soomjae.features.profile.data.source.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.core.remote.networking.put
import com.parksupark.soomjae.features.profile.data.model.dto.request.CheckNicknameDuplicateRequest
import com.parksupark.soomjae.features.profile.data.model.dto.request.PutProfileRequest
import com.parksupark.soomjae.features.profile.data.model.dto.response.CheckNicknameDuplicateResponse
import com.parksupark.soomjae.features.profile.data.model.dto.response.ProfileResponse
import io.ktor.client.HttpClient

internal class ProfileRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun fetchProfile(memberId: Long): Either<DataFailure.Network, ProfileResponse> {
        return httpClient.get<ProfileResponse>(
            route = "/v1/member/${memberId}/profiles"
        )
    }

    suspend fun putProfile(request: PutProfileRequest): Either<DataFailure.Network, ProfileResponse> {
        return httpClient.put<PutProfileRequest, ProfileResponse>(
            route = "/v1/member",
            body = request,
        )
    }

    suspend fun checkNicknameDuplicate(
        request: CheckNicknameDuplicateRequest,
    ): Either<DataFailure, CheckNicknameDuplicateResponse> {
        return httpClient.post<CheckNicknameDuplicateRequest, CheckNicknameDuplicateResponse>(
            route = "/v1/member/profiles/check-duplicate-nickname",
            body = request,
        )
    }
}
