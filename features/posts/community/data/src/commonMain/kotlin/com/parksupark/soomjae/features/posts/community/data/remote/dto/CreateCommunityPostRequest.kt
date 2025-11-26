package com.parksupark.soomjae.features.posts.community.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateCommunityPostRequest(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("category") val categoryId: Long?,
    @SerialName("location") val locationCode: Long?,
)
