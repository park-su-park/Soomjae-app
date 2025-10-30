package com.parksupark.soomjae.features.posts.common.presentation.models

import com.parksupark.soomjae.features.posts.common.domain.models.Category

data class CategoryUi(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    val children: List<CategoryUi>,
)

fun Category.toUi(): CategoryUi = CategoryUi(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { it.toUi() },
)

fun CategoryUi.toDomain(): Category = Category(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { it.toDomain() },
)
