package com.parksupark.soomjae.features.posts.common.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.data.dtos.toLocation
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteLocationSource
import com.parksupark.soomjae.features.posts.common.domain.models.Location
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository

internal class DefaultLocationRepository(
    private val remoteLocationSource: RemoteLocationSource,
) : LocationRepository {
    override suspend fun getAllLocations(): Either<DataFailure.Network, List<Location>> = remoteLocationSource.getAllLocations()
        .map { locations ->
            locations.map {
                it.toLocation()
            }
        }
}
