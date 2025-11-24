package com.parksupark.soomjae.features.posts.member.presentation.post_list.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.post.model.AuthorUi
import com.parksupark.soomjae.core.presentation.ui.post.model.toUi
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// TODO: Add more fields later
@OptIn(ExperimentalTime::class)
data class MemberPostUi(
    val id: Long,
    val author: AuthorUi,
    val content: String,
    val images: ImmutableList<String>,
    val createdAt: Instant,
    val isLiked: Boolean,
    val likeCount: Long,
    val commentCount: Long,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

@OptIn(ExperimentalTime::class)
internal fun MemberPost.toMemberPostUi(): MemberPostUi = MemberPostUi(
    id = this.id,
    author = this.author.toUi(),
    content = this.content,
    images = this.images.toImmutableList(),
    createdAt = this.createdAt,
    isLiked = this.isLiked,
    likeCount = this.likeCount,
    commentCount = this.commentCount,
)
