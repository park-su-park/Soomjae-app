package com.parksupark.soomjae.core.image.domain.utils

import com.parksupark.soomjae.core.image.domain.models.ImageData
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.mimeType
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes

private const val DEFAULT_MEME_TYPE = "image/png"

suspend fun PlatformFile.toImageData(): ImageData = ImageData(
    bytes = this.readBytes(),
    fileName = this.name,
    mimeType = this.mimeType()?.toString() ?: DEFAULT_MEME_TYPE,
)
