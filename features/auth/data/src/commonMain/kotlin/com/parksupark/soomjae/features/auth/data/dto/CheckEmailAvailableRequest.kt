package com.parksupark.soomjae.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CheckEmailAvailableRequest(
    val email: String,
)
