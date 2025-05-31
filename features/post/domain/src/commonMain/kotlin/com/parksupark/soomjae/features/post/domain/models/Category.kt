package com.parksupark.soomjae.features.post.domain.models

data class Category(
    val id: String,
    val name: String,
    val hierarchy: Int,
    val children: List<Category>,
    val parentId: String? = null,
)
