package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Location

interface LocationRepository {
    suspend fun getAllLocations(): Either<DataFailure.Network, List<Location>>
}
