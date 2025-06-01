package com.parksupark.soomjae.features.post.presentation.models

import com.parksupark.soomjae.features.post.domain.models.Category

internal data class CategoryUi(
    val id: Long,
    val name: String,
)

internal fun Category.toUi() = CategoryUi(
    id = id,
    name = name,
)
