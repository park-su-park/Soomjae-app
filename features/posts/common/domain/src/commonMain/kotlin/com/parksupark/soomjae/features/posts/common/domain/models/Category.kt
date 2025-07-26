package com.parksupark.soomjae.features.posts.common.domain.models

data class Category(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    val children: List<Category>,
    val parentId: Long? = null,
)
