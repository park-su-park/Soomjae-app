package com.parksupark.soomjae.core.analytics.di

import com.parksupark.soomjae.core.analytics.helpers.AnalyticsHelper
import com.parksupark.soomjae.core.analytics.helpers.StubAnalyticsHelper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    // TODO: Implement desktop analytics helper
    singleOf(::StubAnalyticsHelper).bind<AnalyticsHelper>()
}
