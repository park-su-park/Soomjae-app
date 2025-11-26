package com.parksupark.soomjae.core.image.data.processor

import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.image.domain.ImageProcessor
import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.ImageProcessingPolicy
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.compressImage
import kotlinx.coroutines.withContext

internal class DefaultImageProcessor(
    private val dispatcher: SoomjaeDispatcher,
) : ImageProcessor {
    override suspend fun process(
        image: ImageData,
        policy: ImageProcessingPolicy,
    ): ImageData = withContext(dispatcher.default) {
        val processedBytes = FileKit.compressImage(
            bytes = image.bytes,
            quality = policy.compressionQuality,
            maxWidth = policy.maxWidth,
        )

        image.copy(bytes = processedBytes)
    }
}
