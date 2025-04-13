package com.parksupark.soomjae.core.common.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface SoomjaeDispatcher : CoroutineScope {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = main + SupervisorJob()
}
