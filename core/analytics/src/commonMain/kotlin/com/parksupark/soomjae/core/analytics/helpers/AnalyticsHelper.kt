package com.parksupark.soomjae.core.analytics.helpers

import com.parksupark.soomjae.core.analytics.models.AnalyticsEvent

interface AnalyticsHelper {
    fun logEvent(event: AnalyticsEvent)
}
