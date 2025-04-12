package com.parksupark.soomjae.core.common.di

import com.parksupark.soomjae.core.common.coroutines.AppSoomjaeDispatcher
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreCommonModule = module {
    includes(dispatcherModule)
}

private val dispatcherModule = module {
    singleOf(::AppSoomjaeDispatcher) bind SoomjaeDispatcher::class
}
