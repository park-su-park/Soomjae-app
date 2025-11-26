package com.parksupark.soomjae.core.presentation.ui.post.model

import com.parksupark.soomjae.core.domain.post.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

data class CategoryUi(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    val children: ImmutableList<CategoryUi>,
)

fun Category.toUi(): CategoryUi = CategoryUi(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { it.toUi() }.toPersistentList(),
)

fun CategoryUi.toDomain(): Category = Category(
    id = id,
    name = name,
    hierarchy = hierarchy,
    children = children.map { it.toDomain() },
)
