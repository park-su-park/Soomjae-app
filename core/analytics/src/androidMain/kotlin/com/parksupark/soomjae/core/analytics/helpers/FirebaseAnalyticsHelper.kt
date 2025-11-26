package com.parksupark.soomjae.core.analytics.helpers

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.parksupark.soomjae.core.analytics.models.AnalyticsEvent

class FirebaseAnalyticsHelper(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            for (extra in event.extras) {
                param(
                    key = extra.key.take(40),
                    value = extra.value.take(100),
                )
            }
        }
    }
}
