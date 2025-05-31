package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.domain.models.Category

interface CategoryRemoteSource {
    suspend fun getAllCategories(): Either<DataFailure.Network, List<Category>>
}
