package com.parksupark.soomjae.features.posts.common.presentation.models

import com.parksupark.soomjae.core.domain.post.model.Location

data class LocationUi(
    val code: Long,
    val name: String,
)

fun Location.toLocationUi() = LocationUi(
    code = code,
    name = name,
)

fun LocationUi.toDomain() = Location(
    code = code,
    name = name,
)
