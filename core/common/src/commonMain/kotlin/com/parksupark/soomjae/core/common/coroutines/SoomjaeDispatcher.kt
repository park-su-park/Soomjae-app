package com.parksupark.soomjae.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface SoomjaeDispatcher: CoroutineScope {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = main + SupervisorJob()
}
