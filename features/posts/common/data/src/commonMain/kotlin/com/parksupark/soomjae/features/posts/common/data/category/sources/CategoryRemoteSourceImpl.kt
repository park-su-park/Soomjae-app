package com.parksupark.soomjae.features.posts.common.data.category.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Category
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.category.dtos.CategoryResponse
import com.parksupark.soomjae.features.posts.common.data.category.dtos.toDomain
import io.ktor.client.HttpClient

internal class CategoryRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CategoryRemoteSource {
    override suspend fun getAllCategories(): Either<DataFailure.Network, Map<Long, Category>> =
        httpClient.get<CategoryResponse>(
            route = "/v1/categories/all",
        ).map { response ->
            response.children.flatMap { category ->
                flattenCategory(category)
            }.associateBy { it.id }
        }

    private fun flattenCategory(category: CategoryResponse): List<Category> =
        listOf(category.toDomain()) + category.children.flatMap {
            flattenCategory(it)
        }
}
