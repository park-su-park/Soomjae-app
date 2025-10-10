package com.parksupark.soomjae.core.image.data.repositories

import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.image.data.datasources.NetworkUploader
import com.parksupark.soomjae.core.image.domain.ImageRepository
import com.parksupark.soomjae.core.image.domain.models.ImageData
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

private const val TAG = "DefaultImageRepository"

@OptIn(InternalAPI::class)
internal class DefaultImageRepository(
    private val dispatcher: SoomjaeDispatcher,
    private val uploader: NetworkUploader,
) : ImageRepository {
    override fun uploadWithProgress(image: ImageData): Flow<UploadProgress> = channelFlow {
        trySend(UploadProgress.InProgress(0f))

        val result = uploader.uploadBinaryData(image) { fraction ->
            trySend(UploadProgress.InProgress(fraction.coerceIn(0f, 1f)))
        }

        result.fold(
            ifLeft = { failure ->
                trySend(UploadProgress.Error(failure))
            },
            ifRight = { response ->
                trySend(UploadProgress.Success(response))
            },
        )
    }.catch { e ->
        Logger.e(TAG, e)
        emit(UploadProgress.Error(DataFailure.Network.UNKNOWN))
    }.flowOn(dispatcher.io)
}
