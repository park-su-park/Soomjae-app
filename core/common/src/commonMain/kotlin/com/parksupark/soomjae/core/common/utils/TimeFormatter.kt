package com.parksupark.soomjae.core.common.utils

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat

val LocalTime.Formats.HH_MM: DateTimeFormat<LocalTime>
    get() = LocalTime.Format {
        hour()
        chars(":")
        minute()
    }
