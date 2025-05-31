package com.parksupark.soomjae.features.post.data.dtos

import kotlinx.serialization.Serializable

@Serializable
internal data class CommunityPostsResponse(
    val posts: List<CommunityPostResponse>,
)
