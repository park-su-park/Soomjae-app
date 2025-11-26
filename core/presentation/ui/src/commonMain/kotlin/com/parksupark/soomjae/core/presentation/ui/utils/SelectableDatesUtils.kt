package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePickerDefaults.rememberFutureDates(
    startInstant: Instant,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): SelectableDates {
    val localDate = remember(startInstant, timeZone) {
        startInstant.toLocalDateTime(timeZone).date
    }
    return rememberFutureDates(startDate = localDate, timeZone = timeZone)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePickerDefaults.rememberFutureDates(
    startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): SelectableDates = remember(startDate, timeZone) {
    val anchorEpochMillis = startDate.atStartOfDayIn(timeZone).toEpochMilliseconds()
    val anchorYear = startDate.year

    object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean =
            utcTimeMillis >= anchorEpochMillis

        override fun isSelectableYear(year: Int): Boolean = year >= anchorYear
    }
}
