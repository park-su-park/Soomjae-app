package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_friendly_month_day_time
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_friendly_today_time
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_friendly_year_month_day
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_remaining_days
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_remaining_expired
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_remaining_hours
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_remaining_minutes
import com.parksupark.soomjae.core.presentation.ui.resources.datetime_until_combined
import com.parksupark.soomjae.core.presentation.ui.resources.value
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalTime::class)
fun Instant.formatRemainingTime(
    now: Instant = Clock.System.now(),
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): RemainingDateInfo {
    val nowLocal = now.toLocalDateTime(timeZone)
    val targetLocal = this.toLocalDateTime(timeZone)

    val sameDate = targetLocal.date == nowLocal.date
    val sameYear = targetLocal.year == nowLocal.year

    val duration = this - now // Kotlinx Duration
    val remaining = when {
        duration.isNegative() -> RemainingDateInfo.Remaining.Expired
        duration.inWholeMinutes < 60 -> RemainingDateInfo.Remaining.Minutes(
            duration.inWholeMinutes.toInt(),
        )

        duration.inWholeHours < 24 -> RemainingDateInfo.Remaining.Hours(
            duration.inWholeHours.toInt(),
        )
        duration.inWholeDays < 7 -> RemainingDateInfo.Remaining.Days(duration.inWholeDays.toInt())
        else -> null
    }

    val formatType = when {
        sameDate -> RemainingDateInfo.Type.Today
        sameYear -> RemainingDateInfo.Type.ThisYear
        else -> RemainingDateInfo.Type.OtherYear
    }

    return RemainingDateInfo(
        year = targetLocal.year,
        month = targetLocal.month.number,
        day = targetLocal.day,
        hour = targetLocal.hour,
        minute = targetLocal.minute,
        type = formatType,
        remaining = remaining,
    )
}

@OptIn(ExperimentalTime::class)
@Composable
fun Instant.rememberRemainingTimeText(
    now: Instant = Clock.System.now(),
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): String {
    val info = remember(this, now, timeZone) { formatRemainingTime(now, timeZone) }

    val timeText = "${info.hour.pad2()}:${info.minute.pad2()}"
    val dateText = when (info.type) {
        RemainingDateInfo.Type.Today ->
            Res.string.datetime_friendly_today_time.value(timeText)

        RemainingDateInfo.Type.ThisYear -> Res.string.datetime_friendly_month_day_time.value(
            info.month,
            info.day,
            timeText,
        )

        RemainingDateInfo.Type.OtherYear -> stringResource(
            Res.string.datetime_friendly_year_month_day,
            info.year,
            info.month,
            info.day,
        )
    }

    val remainingText = when (val r = info.remaining) {
        is RemainingDateInfo.Remaining.Minutes -> Res.string.datetime_remaining_minutes.value(
            r.value,
        )

        is RemainingDateInfo.Remaining.Hours -> Res.string.datetime_remaining_hours.value(r.value)

        is RemainingDateInfo.Remaining.Days -> Res.string.datetime_remaining_days.value(r.value)

        is RemainingDateInfo.Remaining.Expired -> Res.string.datetime_remaining_expired.value

        null -> null
    }

    val str = remainingText?.let {
        Res.string.datetime_until_combined.value(dateText, it)
    }
    return remember(dateText, remainingText) {
        str ?: dateText
    }
}

private fun Int.pad2(): String = if (this < 10) "0$this" else this.toString()

data class RemainingDateInfo(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val type: Type,
    val remaining: Remaining?,
) {
    enum class Type { Today, ThisYear, OtherYear }

    sealed class Remaining {
        data class Minutes(val value: Int) : Remaining()

        data class Hours(val value: Int) : Remaining()

        data class Days(val value: Int) : Remaining()

        data object Expired : Remaining()
    }
}
