package com.parksupark.soomjae.core.analytics.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.parksupark.soomjae.core.analytics.helpers.AnalyticsHelper
import com.parksupark.soomjae.core.analytics.helpers.StubAnalyticsHelper

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    StubAnalyticsHelper()
}
