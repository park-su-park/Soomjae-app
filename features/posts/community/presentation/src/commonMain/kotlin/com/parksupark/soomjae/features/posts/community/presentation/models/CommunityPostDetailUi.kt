package com.parksupark.soomjae.features.posts.community.presentation.models

import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetail
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetailWithLiked
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class CommunityPostDetailUi(
    val post: CommunityPostUi,
    val isLiked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val comments: ImmutableList<CommentUi>,
)

@OptIn(ExperimentalTime::class)
private fun CommunityPostDetail.toUi(): CommunityPostUi = CommunityPostUi(
    id = this.id,
    title = this.title,
    content = this.content,
    author = this.author.toUi(),
    createdAt = this.createdAt,
)

// TODO: Implement Logic to get like count and comment count
internal fun CommunityPostDetailWithLiked.toDetailUi(): CommunityPostDetailUi = CommunityPostDetailUi(
    post = this.post.toUi(),
    isLiked = this.isLiked,
    likeCount = 0,
    commentCount = 0,
    comments = this.post.comments.map { it.toUi() }.toImmutableList(),
)
