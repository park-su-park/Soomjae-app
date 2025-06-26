package com.parksupark.soomjae.features.post.presentation.post.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import kotlinx.datetime.Instant

internal data class CommunityPostUi(
    val id: Long,
    val title: String,
    val content: String,
    val author: AuthorUi,
    val createdAt: Instant,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

internal fun CommunityPost.toUi(): CommunityPostUi = CommunityPostUi(
    id = id,
    title = title,
    content = content,
    author = author.toUi(),
    createdAt = createdAt,
)
