package com.parksupark.soomjae.features.post.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.data.sources.CategoryRemoteSource
import com.parksupark.soomjae.features.post.domain.models.Category
import com.parksupark.soomjae.features.post.domain.repositories.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryRemoteSource: CategoryRemoteSource,
) : CategoryRepository {
    override suspend fun getAllCategories(): Either<DataFailure.Network, Map<Long, Category>> = categoryRemoteSource.getAllCategories()
}
