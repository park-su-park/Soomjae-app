package com.parksupark.soomjae.features.posts.member.data.model.dto.request

import kotlinx.serialization.Serializable

@Serializable
internal data class PostMemberPostRequest(
    val content: String,
    val imageUrls: List<String>,
)
