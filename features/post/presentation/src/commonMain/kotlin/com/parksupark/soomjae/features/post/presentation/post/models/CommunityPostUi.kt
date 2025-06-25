package com.parksupark.soomjae.features.post.presentation.post.models

import com.parksupark.soomjae.features.post.domain.models.CommunityPost

internal data class CommunityPostUi(
    val id: Long,
    val title: String,
    val content: String,
    val author: AuthorUi,
    val createdAt: String,
)

// TODO: Fix formatting logic of createdAt
internal fun CommunityPost.toUi(): CommunityPostUi = CommunityPostUi(
    id = id,
    title = title,
    content = content,
    author = author.toUi(),
    createdAt = createdAt.toString(),
)
