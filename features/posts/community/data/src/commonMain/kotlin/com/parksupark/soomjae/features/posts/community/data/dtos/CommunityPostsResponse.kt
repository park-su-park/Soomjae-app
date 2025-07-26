package com.parksupark.soomjae.features.posts.community.data.dtos

import kotlinx.serialization.Serializable

@Serializable
internal data class CommunityPostsResponse(
    val posts: List<CommunityPostResponse>,
)
