package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_minutes_ago
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_soon
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_years_ago
import com.parksupark.soomjae.core.presentation.ui.resources.value
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalTestApi::class, ExperimentalTime::class)
internal class InstantTest : FunSpec({
    context("toRelativeTimeString") {
        val now = LocalDate(2025, 6, 26).atEndOfDayIn(TimeZone.UTC)
        val nowLocalDate = now.toLocalDateTime(TimeZone.UTC).date
        val timeZone = TimeZone.UTC

        test("should return soon") {
            runComposeUiTest {
                setContent {
                    val date = now.minus(1.seconds)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe Res.string.datetime_soon.value
                }
            }
        }

        test("should return minutes ago") {
            runComposeUiTest {
                setContent {
                    val minutesAgo = 2
                    val date = now.minus(minutesAgo.minutes)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe stringResource(Res.string.datetime_minutes_ago, minutesAgo)
                }
            }
        }

        test("should return time in HH:mm format") {
            runComposeUiTest {
                setContent {
                    val hour = 10
                    val minute = 30
                    val date =
                        LocalDateTime(nowLocalDate, LocalTime(hour, minute)).toInstant(TimeZone.UTC)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe "${hour.toTwoDigitString()}:${minute.toTwoDigitString()}"
                }
            }
        }

        test("should return date in MM/dd format") {
            runComposeUiTest {
                setContent {
                    val month = 6
                    val day = 21
                    val date = LocalDate(nowLocalDate.year, month, day).atStartOfDayIn(timeZone)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe "${month.toTwoDigitString()}/${day.toTwoDigitString()}"
                }
            }
        }

        test("should return date in yy/MM/dd format") {
            runComposeUiTest {
                setContent {
                    val year = 2024
                    val month = 12
                    val day = 21
                    val date = LocalDate(year, month, day).atStartOfDayIn(timeZone)
                    println(now)
                    println(date)
                    println(now - date)
                    println((now - date).inWholeSeconds)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe "${
                        year.toString().takeLast(2)
                    }/${month.toTwoDigitString()}/${day.toTwoDigitString()}"
                }
            }
        }

        test("should return years ago") {
            runComposeUiTest {
                setContent {
                    val yearsAgo = 2
                    val date = now.minus(365.days * yearsAgo)

                    val result = date.toRelativeTimeString(now, timeZone)

                    result shouldBe Res.string.datetime_years_ago.value(yearsAgo)
                }
            }
        }
    }
})

@OptIn(ExperimentalTime::class)
private fun LocalDate.atEndOfDayIn(timeZone: TimeZone): Instant =
    this.atStartOfDayIn(timeZone).plus(1.days - 1.seconds)

private fun Number.toTwoDigitString(): String = this.toString().padStart(2, '0')
