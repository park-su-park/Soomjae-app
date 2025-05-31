package com.parksupark.soomjae.features.post.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.domain.models.Category

interface CategoryRepository {
    suspend fun getAllCategories(): Either<DataFailure.Network, Map<Long, Category>>
}
