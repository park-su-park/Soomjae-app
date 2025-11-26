package com.parksupark.soomjae.core.common.utils

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

suspend fun <T> Flow<T?>.firstOrNullWithTimeout(timeoutMs: Long): T? =
    withTimeoutOrNull(timeoutMs) { first() }

suspend fun <T> Flow<T?>.firstOrNullWithTimeout(timeout: Duration): T? =
    withTimeoutOrNull(timeout) { first() }
