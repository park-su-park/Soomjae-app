package com.parksupark.soomjae.features.posts.member.data.dtos.request

import kotlinx.serialization.Serializable

@Serializable
data class PostMemberPostRequest(
    val content: String,
    val imageUrls: List<String>,
)
