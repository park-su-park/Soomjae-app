package com.parksupark.soomjae.features.posts.common.presentation.models

import com.parksupark.soomjae.features.posts.common.domain.models.Location

data class LocationUi(
    val code: Long,
    val name: String,
)

fun Location.toLocationUi() = LocationUi(
    code = code,
    name = name,
)
