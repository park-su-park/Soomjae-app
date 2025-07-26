package com.parksupark.soomjae.core.domain.failures

sealed interface DataFailure {
    enum class Network : DataFailure {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataFailure {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN,
    }
}
