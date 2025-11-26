package com.parksupark.soomjae.core.analytics.helpers

import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.analytics.models.AnalyticsEvent

class StubAnalyticsHelper : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        Logger.d(TAG) { "Received analytics event: $event" }
    }
}

private const val TAG = "StubAnalyticsHelper"
