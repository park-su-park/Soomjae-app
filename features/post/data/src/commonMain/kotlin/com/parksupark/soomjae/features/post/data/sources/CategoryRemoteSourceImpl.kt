package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.post.data.dtos.CategoriesResponse
import com.parksupark.soomjae.features.post.data.dtos.toDomain
import com.parksupark.soomjae.features.post.domain.models.Category
import io.ktor.client.HttpClient

class CategoryRemoteSourceImpl(
    private val httpClient: HttpClient,
) : CategoryRemoteSource {
    override suspend fun getAllCategories(): Either<DataFailure.Network, List<Category>> = httpClient.get<CategoriesResponse>(
        route = "/v1/categories/all",
    ).map { response ->
        response.categories.map { it.toDomain() }
    }
}
