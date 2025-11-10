package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.common.utils.toEpochMilliseconds
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeDatePicker
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeDatePickerDialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTimePicker
import com.parksupark.soomjae.core.presentation.designsystem.components.TimePickerDialog
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_cancel
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_confirm
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
@Composable
fun MeetingDatePicker(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    initialDate: LocalDate = Clock.System.now().toLocalDateTime(timeZone).date,
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.toEpochMilliseconds(timeZone),
    )

    SoomjaeDatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            SoomjaeTextButton(
                onClick = {
                    state.selectedDateMillis?.let { millis ->
                        val date = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(timeZone)
                            .date

                        onConfirm(date)
                    }
                },
                enabled = state.selectedDateMillis != null,
            ) {
                Text(Res.string.meeting_create_datetime_dialog_confirm.value)
            }
        },
        dismissButton = {
            SoomjaeTextButton(
                onClick = onDismiss,
            ) {
                Text(Res.string.meeting_create_datetime_dialog_cancel.value)
            }
        },
        modifier = modifier,
    ) {
        SoomjaeDatePicker(
            state = state,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun MeetingTimePicker(
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    initialTime: LocalTime = Clock.System.now().toLocalDateTime(timeZone).time,
) {
    val state = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
    )

    TimePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            SoomjaeTextButton(
                onClick = {
                    val time = LocalTime(
                        hour = state.hour,
                        minute = state.minute,
                    )
                    onConfirm(time)
                },
            ) {
                Text(Res.string.meeting_create_datetime_dialog_confirm.value)
            }
        },
        title = { },
        modifier = modifier,
    ) {
        SoomjaeTimePicker(
            state = state,
        )
    }
}
