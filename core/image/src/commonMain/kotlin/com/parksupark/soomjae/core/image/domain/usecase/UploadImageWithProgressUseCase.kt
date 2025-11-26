package com.parksupark.soomjae.core.image.domain.usecase

import com.parksupark.soomjae.core.image.domain.ImageRepository
import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import kotlinx.coroutines.flow.Flow

class UploadImageWithProgressUseCase(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(imageData: ImageData): Flow<UploadProgress> =
        imageRepository.uploadWithProgress(image = imageData)
}
