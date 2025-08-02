package com.parksupark.soomjae.features.posts.common.data.location.dtos

import com.parksupark.soomjae.features.posts.common.domain.models.Location
import kotlinx.serialization.Serializable

@Serializable
internal data class LocationResponse(
    val code: Long,
    val name: String,
)

internal fun LocationResponse.toLocation(): Location = Location(
    code = code,
    name = name,
)
