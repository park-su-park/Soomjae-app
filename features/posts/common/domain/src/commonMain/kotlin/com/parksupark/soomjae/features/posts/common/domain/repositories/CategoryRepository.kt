package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Category

interface CategoryRepository {
    suspend fun getAllCategories(): Either<DataFailure.Network, Map<Long, Category>>
}
