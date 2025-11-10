package com.parksupark.soomjae.core.common.utils

import kotlinx.datetime.LocalTime

fun LocalTime.trimToHour(): LocalTime = LocalTime(hour, 0, 0)

fun LocalTime.trimToMinute(): LocalTime = LocalTime(hour, minute)

fun LocalTime.trimToSecond(): LocalTime = LocalTime(hour, minute, second)
