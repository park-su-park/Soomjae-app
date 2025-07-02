package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_minutes_ago
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_soon
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_years_ago
import com.parksupark.soomjae.core.presentation.ui.resources.value
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private const val MINUTE_IN_SECONDS = 60
private const val HOUR_IN_SECONDS = 60 * MINUTE_IN_SECONDS
private const val YEAR_IN_SECONDS = 31_536_000

private const val BASE_YEAR = 2000

@OptIn(ExperimentalTime::class)
@Composable
fun Instant.toRelativeTimeString(
    now: Instant = Clock.System.now(),
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): String {
    val instant = this
    val differenceInSeconds = (now - instant).inWholeSeconds

    return when {
        differenceInSeconds < MINUTE_IN_SECONDS -> Res.string.datetime_soon.value
        differenceInSeconds < HOUR_IN_SECONDS -> Res.string.datetime_minutes_ago.value(differenceInSeconds / MINUTE_IN_SECONDS)

        else -> {
            val nowDateTime = now.toLocalDateTime(timeZone)
            val instantDateTime = instant.toLocalDateTime(timeZone)

            return when {
                nowDateTime.date == instantDateTime.date -> instantDateTime.time.format(
                    LocalTime.Format {
                        hour()
                        char(':')
                        minute()
                    },
                )

                nowDateTime.date.year == instantDateTime.date.year -> instantDateTime.date.format(
                    LocalDate.Format {
                        monthNumber()
                        char('/')
                        day()
                    },
                )

                differenceInSeconds < YEAR_IN_SECONDS -> instantDateTime.date.format(
                    LocalDate.Format {
                        yearTwoDigits(BASE_YEAR)
                        char('/')
                        monthNumber()
                        char('/')
                        day()
                    },
                )

                else -> Res.string.datetime_years_ago.value(differenceInSeconds / YEAR_IN_SECONDS)
            }
        }
    }
}
