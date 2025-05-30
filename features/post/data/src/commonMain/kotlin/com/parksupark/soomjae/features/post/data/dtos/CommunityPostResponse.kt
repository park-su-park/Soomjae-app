package com.parksupark.soomjae.features.post.data.dtos

import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import kotlinx.serialization.Serializable

@Serializable
internal data class CommunityPostResponse(
    val id: String,
)

internal fun CommunityPostResponse.toModel(): CommunityPost = CommunityPost(
    id = id,
)
