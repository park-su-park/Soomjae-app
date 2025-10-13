package com.parksupark.soomjae.core.image.data.datasources

import arrow.core.Either
import arrow.core.raise.either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.image.data.dto.response.UploadResponse
import com.parksupark.soomjae.core.image.data.dto.response.toRemoteFile
import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.RemoteFile
import com.parksupark.soomjae.core.remote.networking.constructRoute
import com.parksupark.soomjae.core.remote.networking.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.utils.io.InternalAPI

@OptIn(InternalAPI::class)
internal class NetworkUploader(
    private val httpClient: HttpClient,
) {
    suspend fun uploadBinaryData(
        image: ImageData,
        onProgress: suspend (Float) -> Unit,
    ): Either<DataFailure.Network, RemoteFile> = either {
        safeCall<UploadResponse> {
            httpClient.submitFormWithBinaryData(
                url = constructRoute("/v1/images"),
                formData = createImageFormData(image),
            ) {
                onUpload { sent, total ->
                    if (total != null && total > 0L) {
                        onProgress(sent.toFloat() / total)
                    }
                }
            }
        }.bind()
            .toRemoteFile()
    }

    private fun createImageFormData(image: ImageData): List<PartData> = formData {
        append(
            key = "file",
            value = image.bytes,
            headers = Headers.build {
                append(HttpHeaders.ContentType, image.mimeType)
                append(HttpHeaders.ContentDisposition, "filename=${image.fileName}")
            },
        )
    }
}
