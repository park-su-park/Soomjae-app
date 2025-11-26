package com.parksupark.soomjae.core.image.domain.models

data class ImageProcessingPolicy(
    val maxWidth: Int = 1080,
    val compressionQuality: Int = 80,
    val keepAspect: Boolean = true,
)
