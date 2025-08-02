package com.parksupark.soomjae.features.posts.common.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.data.dtos.LocationResponse

internal interface RemoteLocationSource {
    suspend fun getAllLocations(): Either<DataFailure.Network, List<LocationResponse>>
}
