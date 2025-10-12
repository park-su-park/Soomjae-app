package com.parksupark.soomjae.features.posts.community.presentation.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class CommunityPostUi(
    val id: Long,
    val title: String,
    val content: String,
    val author: AuthorUi,
    val likeCount: Int,
    val isUserLiked: Boolean,
    val commentCount: Int,
    val createdAt: Instant,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

@OptIn(ExperimentalTime::class)
internal fun CommunityPost.toUi(): CommunityPostUi = CommunityPostUi(
    id = id,
    title = title,
    content = content,
    author = author.toUi(),
    createdAt = createdAt,
    likeCount = this.likeCount,
    isUserLiked = this.isUserLiked,
    commentCount = Random.nextInt(20),
)
