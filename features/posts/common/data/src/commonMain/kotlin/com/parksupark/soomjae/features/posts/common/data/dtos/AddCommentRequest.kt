package com.parksupark.soomjae.features.posts.common.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequest(
    val content: String,
)
