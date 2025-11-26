package com.parksupark.soomjae.features.profile.data.model.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class IntroductionPostRequest(
    @SerialName("content") val html: String,
)
