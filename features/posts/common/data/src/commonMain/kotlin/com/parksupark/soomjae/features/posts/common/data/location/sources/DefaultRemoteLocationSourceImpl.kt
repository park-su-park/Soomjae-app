package com.parksupark.soomjae.features.posts.common.data.location.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.location.dtos.LocationResponse
import io.ktor.client.HttpClient

internal class DefaultRemoteLocationSourceImpl(
    private val httpClient: HttpClient,
) : RemoteLocationSource {
    override suspend fun getAllLocations(): Either<DataFailure.Network, List<LocationResponse>> =
        httpClient.get<List<LocationResponse>>(
            route = "/v1/locations",
        )
}
