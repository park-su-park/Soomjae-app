package com.parksupark.soomjae.features.posts.common.data.location.dtos

import com.parksupark.soomjae.core.domain.post.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val code: Long,
    val name: String,
)

fun LocationResponse.toLocation(): Location = Location(
    code = code,
    name = name,
)
