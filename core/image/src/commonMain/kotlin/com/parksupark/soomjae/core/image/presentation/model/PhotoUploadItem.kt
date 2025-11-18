package com.parksupark.soomjae.core.image.presentation.model

import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.core.common.utils.NanoId
import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.core.image.domain.utils.toImageData
import io.github.vinceglb.filekit.PlatformFile

@Immutable
data class PhotoUploadItem(
    val id: String,
    val platformImage: PlatformFile,
    val localImage: ImageData,
    val uploadProgress: UploadProgress = UploadProgress.Idle,
)

suspend fun PlatformFile.toPhotoUploadItem(): PhotoUploadItem {
    val id = NanoId.generate()

    return PhotoUploadItem(
        id = id,
        platformImage = this,
        localImage = this.toImageData(id),
        uploadProgress = UploadProgress.Idle,
    )
}
