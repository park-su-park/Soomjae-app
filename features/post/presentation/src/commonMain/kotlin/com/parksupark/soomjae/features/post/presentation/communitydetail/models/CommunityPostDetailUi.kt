package com.parksupark.soomjae.features.post.presentation.communitydetail.models

import com.parksupark.soomjae.features.post.domain.models.CommunityPostDetail
import com.parksupark.soomjae.features.post.domain.models.CommunityPostDetailWithLiked
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi
import com.parksupark.soomjae.features.post.presentation.post.models.toUi

internal data class CommunityPostDetailUi(
    val post: CommunityPostUi,
    val isLiked: Boolean,
    val likeCount: Long,
    val commentCount: Long,
)

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
)
