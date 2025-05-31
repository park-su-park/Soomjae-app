package com.parksupark.soomjae.features.post.data.dtos

import com.parksupark.soomjae.features.post.domain.models.Category
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryResponse(
    val id: String,
    val name: String,
    val hierarchy: Int,
    val children: List<CategoryResponse>,
)

internal fun CategoryResponse.toDomain(parentId: String? = null): Category = Category(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { child -> child.toDomain(id) },
    parentId = parentId,
)
