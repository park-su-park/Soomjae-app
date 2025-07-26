package com.parksupark.soomjae.features.posts.common.presentation.models

import com.parksupark.soomjae.features.posts.common.domain.models.Category

data class CategoryUi(
    val id: Long,
    val name: String,
)

fun Category.toUi() = CategoryUi(
    id = id,
    name = name,
)
