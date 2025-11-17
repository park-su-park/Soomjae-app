package com.parksupark.soomjae.core.image.domain.models

import com.parksupark.soomjae.core.common.utils.NanoId

data class ImageData(
    val id: String = NanoId.generate(),
    val bytes: ByteArray,
    val fileName: String,
    val mimeType: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageData) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
