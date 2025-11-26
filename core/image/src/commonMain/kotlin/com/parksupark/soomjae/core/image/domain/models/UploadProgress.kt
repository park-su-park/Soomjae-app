package com.parksupark.soomjae.core.image.domain.models

import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.core.domain.failures.DataFailure

sealed interface UploadProgress {
    data object Idle : UploadProgress

    @Immutable
    data class InProgress(val fraction: Float) : UploadProgress

    data class Success(val result: RemoteFile) : UploadProgress

    data class Error(val failure: DataFailure.Network) : UploadProgress
}
