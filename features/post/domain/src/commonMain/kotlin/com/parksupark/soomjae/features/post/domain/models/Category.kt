package com.parksupark.soomjae.features.post.domain.models

data class Category(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    val children: List<Category>,
    val parentId: Long? = null,
)
