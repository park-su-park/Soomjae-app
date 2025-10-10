package com.parksupark.soomjae.core.image.domain

import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.ImageProcessingPolicy

interface ImageProcessor {
    suspend fun process(
        image: ImageData,
        policy: ImageProcessingPolicy = ImageProcessingPolicy(),
    ): ImageData
}
