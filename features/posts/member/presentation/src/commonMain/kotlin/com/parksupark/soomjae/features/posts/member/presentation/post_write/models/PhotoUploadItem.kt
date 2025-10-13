package com.parksupark.soomjae.features.posts.member.presentation.post_write.models

import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.core.image.domain.utils.toImageData
import io.github.vinceglb.filekit.PlatformFile

data class PhotoUploadItem(
    val platformImage: PlatformFile,
    val localImage: ImageData,
    val uploadProgress: UploadProgress = UploadProgress.Idle,
)

internal suspend fun PlatformFile.toPhotoUploadItem(): PhotoUploadItem = PhotoUploadItem(
    platformImage = this,
    localImage = this.toImageData(),
    uploadProgress = UploadProgress.Idle,
)
