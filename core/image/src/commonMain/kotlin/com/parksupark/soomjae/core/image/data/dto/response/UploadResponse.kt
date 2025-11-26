package com.parksupark.soomjae.core.image.data.dto.response

import com.parksupark.soomjae.core.image.domain.models.RemoteFile
import kotlinx.serialization.Serializable

@Serializable
internal data class UploadResponse(
    val url: String,
    val fileName: String,
)

internal fun String.toRemoteFile(): RemoteFile = RemoteFile(
    id = this.substringAfterLast("/"),
    url = this,
)

internal fun UploadResponse.toRemoteFile(): RemoteFile = RemoteFile(
    id = fileName,
    url = url,
)
