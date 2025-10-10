package com.parksupark.soomjae.core.analytics.di

import SoomJae.core.analytics.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.parksupark.soomjae.core.analytics.helpers.AnalyticsHelper
import com.parksupark.soomjae.core.analytics.helpers.FirebaseAnalyticsHelper
import com.parksupark.soomjae.core.analytics.helpers.StubAnalyticsHelper
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single { Firebase.analytics }
    single {
        if (BuildConfig.isDebug) {
            StubAnalyticsHelper()
        } else {
            FirebaseAnalyticsHelper(get())
        }
    }.bind<AnalyticsHelper>()
}
