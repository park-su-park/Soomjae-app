package com.parksupark.soomjae.features.posts.common.data.category.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.Category

interface CategoryRemoteSource {
    suspend fun getAllCategories(): Either<DataFailure.Network, Map<Long, Category>>
}
