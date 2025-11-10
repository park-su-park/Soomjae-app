package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.common.utils.HH_MM
import com.parksupark.soomjae.core.common.utils.trimToMinute
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeToggleSwitch
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionLayout
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingFormUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_meeting_label
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MeetingDateForm(
    form: MeetingFormUi,
    onAction: (MeetingPostWriteAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val period = form.period

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val timeFormat = remember { LocalTime.Formats.HH_MM }

    WriteSelectionLayout(
        modifier = modifier.fillMaxWidth(),
        header = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(Res.string.meeting_write_meeting_label.value)

                AllDayToggleSwitch(
                    toggled = period.isAllDay,
                    onToggle = {
                        onAction(MeetingPostWriteAction.OnAllDayToggled(it))
                    },
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = SoomjaeTheme.colorScheme.divider2,
                        shape = RoundedCornerShape(12.dp),
                    ),
            ) {
                val shouldShowTime = !period.isAllDay
                DateTimeRow(
                    label = "시작:",
                    date = period.startDate,
                    time = period.startTime,
                    shouldShowTime = shouldShowTime,
                    onDateClick = { showStartDatePicker = true },
                    onTimeClick = { showStartTimePicker = true },
                    timeFormat = timeFormat,
                )
                SoomjaeHorizontalDivider(color = SoomjaeTheme.colorScheme.divider2)

                DateTimeRow(
                    label = "종료:",
                    date = period.endDate,
                    time = period.endTime,
                    shouldShowTime = shouldShowTime,
                    onDateClick = { showEndDatePicker = true },
                    onTimeClick = { showEndTimePicker = true },
                    timeFormat = timeFormat,
                )
            }
        },
    )

    // --- Date Pickers ---
    if (showStartDatePicker) {
        MeetingDatePicker(
            initialDate = period.startDate,
            onConfirm = { newDate ->
                onAction(
                    MeetingPostWriteAction.OnMeetingPeriodChanged(
                        period = period.copy(startDate = newDate),
                        changedField = MeetingPostWriteAction.PeriodField.StartDate,
                    ),
                )
                showStartDatePicker = false
            },
            onDismiss = { showStartDatePicker = false },
        )
    }

    if (showEndDatePicker) {
        MeetingDatePicker(
            initialDate = period.endDate,
            onConfirm = { newDate ->
                onAction(
                    MeetingPostWriteAction.OnMeetingPeriodChanged(
                        period = period.copy(endDate = newDate),
                        changedField = MeetingPostWriteAction.PeriodField.EndDate,
                    ),
                )
                showEndDatePicker = false
            },
            onDismiss = { showEndDatePicker = false },
        )
    }

    // --- Time Pickers ---
    if (showStartTimePicker) {
        MeetingTimePicker(
            initialTime = period.startTime,
            onConfirm = { newTime ->
                onAction(
                    MeetingPostWriteAction.OnMeetingPeriodChanged(
                        period = period.copy(startTime = newTime),
                        changedField = MeetingPostWriteAction.PeriodField.StartTime,
                    ),
                )
                showStartTimePicker = false
            },
            onDismiss = { showStartTimePicker = false },
        )
    }

    if (showEndTimePicker) {
        MeetingTimePicker(
            initialTime = period.endTime,
            onConfirm = { newTime ->
                onAction(
                    MeetingPostWriteAction.OnMeetingPeriodChanged(
                        period = period.copy(endTime = newTime),
                        changedField = MeetingPostWriteAction.PeriodField.EndTime,
                    ),
                )
                showEndTimePicker = false
            },
            onDismiss = { showEndTimePicker = false },
        )
    }
}

@Composable
private fun AllDayToggleSwitch(
    toggled: Boolean = false,
    onToggle: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { onToggle(!toggled) },
        ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "하루 종일",
            style = SoomjaeTheme.typography.labelM.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
        SoomjaeToggleSwitch(
            toggled = toggled,
            onChangeToggle = onToggle,
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun DateTimeRow(
    label: String,
    date: LocalDate,
    time: LocalTime,
    shouldShowTime: Boolean,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    modifier: Modifier = Modifier,
    timeFormat: DateTimeFormat<LocalTime> = LocalTime.Formats.HH_MM,
) {
    Row(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            modifier = Modifier.width(40.dp),
            color = SoomjaeTheme.colorScheme.text2,
            style = SoomjaeTheme.typography.labelM,
            textAlign = TextAlign.End,
        )
        Spacer(modifier = Modifier.width(8.dp))

        SoomjaeTextButton(
            onClick = onDateClick,
            content = { Text(date.toString()) },
            contentColor = SoomjaeTheme.colorScheme.text1,
        )

        AnimatedVisibility(
            visible = shouldShowTime,
            label = "MeetingDateForm_DateTimeRow_Time",
        ) {
            Spacer(modifier = Modifier.width(4.dp))

            SoomjaeTextButton(
                onClick = onTimeClick,
                content = { Text(time.trimToMinute().format(timeFormat), maxLines = 1) },
                contentColor = SoomjaeTheme.colorScheme.text1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MeetingDateFormPreview() {
    AppTheme {
        MeetingDateForm(
            form = MeetingFormUi(),
            onAction = { },
            modifier = Modifier.padding(16.dp),
        )
    }
}
