package com.parksupark.soomjae.core.common.utils

import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalTime::class)
fun LocalDate.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
    this.atStartOfDayIn(timeZone)
        .plus(12.hours)
        .toEpochMilliseconds()
