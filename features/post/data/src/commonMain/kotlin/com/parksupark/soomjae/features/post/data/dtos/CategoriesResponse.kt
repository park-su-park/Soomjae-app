package com.parksupark.soomjae.features.post.data.dtos

import kotlinx.serialization.Serializable

@Serializable
internal data class CategoriesResponse(
    val categories: List<CategoryResponse>,
)
