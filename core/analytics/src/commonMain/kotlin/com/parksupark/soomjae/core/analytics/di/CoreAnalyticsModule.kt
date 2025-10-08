package com.parksupark.soomjae.core.analytics.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val coreAnalyticsModule = module {
    includes(platformModule)
}
