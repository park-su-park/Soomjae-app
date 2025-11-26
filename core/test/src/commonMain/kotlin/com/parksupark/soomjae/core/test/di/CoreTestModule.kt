package com.parksupark.soomjae.core.test.di

import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.test.coroutines.TestSoomjaeDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dispatcherModule = module {
    singleOf(::TestSoomjaeDispatcher) bind SoomjaeDispatcher::class
}

val coreTestModule = module {
    includes(dispatcherModule)
}
