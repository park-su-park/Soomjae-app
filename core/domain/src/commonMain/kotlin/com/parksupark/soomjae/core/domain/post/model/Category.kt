package com.parksupark.soomjae.core.domain.post.model

data class Category(
    val id: Long,
    val name: String,
    val hierarchy: Int,
    val children: List<Category>,
    val parentId: Long? = null,
)
