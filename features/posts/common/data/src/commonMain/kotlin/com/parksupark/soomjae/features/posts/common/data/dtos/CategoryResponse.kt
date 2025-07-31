package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.domain.models.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    @SerialName("childs") val children: List<CategoryResponse>,
)

fun CategoryResponse.toDomain(parentId: Long? = null): Category = Category(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { child -> child.toDomain(id) },
    parentId = parentId,
)
