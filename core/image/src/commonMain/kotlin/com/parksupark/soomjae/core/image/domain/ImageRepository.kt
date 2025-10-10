package com.parksupark.soomjae.core.image.domain

import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun uploadWithProgress(image: ImageData): Flow<UploadProgress>
}
