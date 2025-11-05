package com.parksupark.soomjae.core.common.utils

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

suspend fun Flow<String?>.firstOrNullWithTimeout(timeoutMs: Long): String? =
    withTimeoutOrNull(timeoutMs) { first() }

suspend fun Flow<String?>.firstOrNullWithTimeout(timeout: Duration): String? =
    withTimeoutOrNull(timeout) { first() }
