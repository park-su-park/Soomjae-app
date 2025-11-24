package com.parksupark.soomjae.features.posts.community.domain.model

import com.parksupark.soomjae.core.domain.models.Member
import com.parksupark.soomjae.core.domain.post.model.Category
import com.parksupark.soomjae.core.domain.post.model.Location
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class CommunityPostEdited(
    val id: Long,
    val title: String,
    val content: String,
    val author: Member,
    val createdAt: Instant,

    val category: Category?,
    val location: Location?,

    val likeCount: Int,
    val isUserLiked: Boolean,

    val commentCount: Int,
)

@OptIn(ExperimentalTime::class)
fun CommunityPostEdited.toCommunityPost(): CommunityPost = CommunityPost(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt,
    categoryName = category?.name,
    locationName = location?.name,
    likeCount = likeCount,
    isUserLiked = isUserLiked,
    commentCount = commentCount,
)

@OptIn(ExperimentalTime::class)
fun CommunityPost.toCommunityPostEdited(
    category: Category?,
    location: Location?,
): CommunityPostEdited = CommunityPostEdited(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt,
    category = category,
    location = location,
    likeCount = likeCount,
    isUserLiked = isUserLiked,
    commentCount = commentCount,
)
