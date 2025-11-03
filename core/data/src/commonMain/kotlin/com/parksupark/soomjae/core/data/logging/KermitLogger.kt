package com.parksupark.soomjae.core.data.logging

import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.domain.logging.SjLogger

object KermitLogger : SjLogger {
    override fun debug(message: String) {
        Logger.d(message)
    }

    override fun debug(
        tag: String,
        message: String,
    ) {
        Logger.d(tag) { message }
    }

    override fun info(message: String) {
        Logger.i(message)
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        Logger.i(tag) { message }
    }

    override fun warn(message: String) {
        Logger.w(message)
    }

    override fun warn(
        tag: String,
        message: String,
    ) {
        Logger.w(tag) { message }
    }

    override fun error(
        message: String,
        throwable: Throwable?,
    ) {
        Logger.e(message, throwable)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        Logger.e(tag, throwable) { message }
    }
}
