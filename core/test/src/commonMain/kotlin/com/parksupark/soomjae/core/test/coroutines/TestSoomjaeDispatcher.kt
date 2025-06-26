package com.parksupark.soomjae.core.test.coroutines

import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestSoomjaeDispatcher : SoomjaeDispatcher {
    override val main: CoroutineDispatcher = UnconfinedTestDispatcher(name = "main")
    override val mainImmediate: CoroutineDispatcher = UnconfinedTestDispatcher(name = "mainImmediate")
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher(name = "io")
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher(name = "default")
    override val unconfined: CoroutineDispatcher = UnconfinedTestDispatcher(name = "unconfined")
}
