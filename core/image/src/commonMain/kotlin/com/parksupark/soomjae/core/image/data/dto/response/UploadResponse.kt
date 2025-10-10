package com.parksupark.soomjae.core.image.data.dto.response

import com.parksupark.soomjae.core.image.domain.models.RemoteFile

internal data class UploadResponse(
    val url: String,
    val fileName: String,
)

internal fun UploadResponse.toRemoteFile(): RemoteFile = RemoteFile(
    id = fileName,
    url = url,
)
