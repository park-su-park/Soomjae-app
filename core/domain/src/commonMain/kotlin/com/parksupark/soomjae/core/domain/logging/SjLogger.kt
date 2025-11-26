package com.parksupark.soomjae.core.domain.logging

interface SjLogger {
    fun debug(message: String)

    fun debug(
        tag: String,
        message: String,
    )

    fun info(message: String)

    fun info(
        tag: String,
        message: String,
    )

    fun warn(message: String)

    fun warn(
        tag: String,
        message: String,
    )

    fun error(
        message: String,
        throwable: Throwable? = null,
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    )
}
