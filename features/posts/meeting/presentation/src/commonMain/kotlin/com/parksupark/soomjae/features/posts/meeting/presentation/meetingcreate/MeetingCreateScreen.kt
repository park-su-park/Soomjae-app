package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.rememberFutureDates
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionButton
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionLayout
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components.DatePickerDialogButton
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components.TimePickerDialogButton
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_button_create_meeting
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_unselected
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_navigate_up_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display_unlimited
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_title
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalTime::class)
@Composable
internal fun MeetingCreateScreen(
    state: MeetingCreateState,
    onAction: (MeetingCreateAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            MeetingCreateTopBar(
                onBackClick = { onAction(MeetingCreateAction.OnBackClick) },
            )
        },
        bottomBar = {
            MeetingCreateBottomBar(
                onClick = { onAction(MeetingCreateAction.OnCreateClick) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MeetingCreateDateTimeSection(
                onAction = onAction,
                startDate = state.meeting.startDate,
                startTime = state.meeting.startTime,
                endDate = state.meeting.endDate,
                endTime = state.meeting.endTime,
            )

            MeetingParticipantCount(maxParticipantCount = state.meeting.maxParticipantCount)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MeetingCreateTopBar(onBackClick: () -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.meeting_create_title.value) },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = Res.string.meeting_create_navigate_up_description.value,
                    )
                },
            )
        },
    )
}

@Composable
private fun MeetingCreateBottomBar(onClick: () -> Unit) {
    SoomjaeButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .navigationBarsPadding()
            .padding(16.dp),
    ) {
        Text(Res.string.meeting_create_button_create_meeting.value)
    }
}

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
private fun MeetingCreateDateTimeSection(
    onAction: (MeetingCreateAction) -> Unit,
    startDate: LocalDate,
    startTime: LocalTime,
    endDate: LocalDate?,
    endTime: LocalTime?,
) {
    val dateFormatter = LocalDate.Formats.ISO

    val timeFormatter = remember {
        LocalTime.Format {
            hour()
            chars(":")
            minute()
        }
    }

    val timeZone = remember {
        TimeZone.currentSystemDefault()
    }

    WriteSelectionLayout(
        label = Res.string.meeting_create_datetime_label.value,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            StartDateTimeSelection(
                onAction = onAction,
                timeZone = timeZone,
                startDate = startDate,
                dateFormatter = dateFormatter,
                startTime = startTime,
                timeFormatter = timeFormatter,
            )

            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)

            EndDateTimeSelection(
                onAction = onAction,
                timeZone = timeZone,
                startDate = startDate,
                endDate = endDate,
                dateFormatter = dateFormatter,
                endTime = endTime,
                timeFormatter = timeFormatter,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RowScope.StartDateTimeSelection(
    onAction: (MeetingCreateAction) -> Unit,
    timeZone: TimeZone,
    startDate: LocalDate,
    dateFormatter: DateTimeFormat<LocalDate>,
    startTime: LocalTime,
    timeFormatter: DateTimeFormat<LocalTime>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatePickerDialogButton(
            onConfirm = { dateInMillis ->
                onAction(MeetingCreateAction.OnStartDateSelected(dateInMillis.toLocalDate(timeZone)))
            },
            selectableDates = DatePickerDefaults.rememberFutureDates(),
        ) { openDialog ->
            SoomjaeTextButton(
                onClick = openDialog,
                content = {
                    Text(text = startDate.format(dateFormatter))
                },
            )
        }

        TimePickerDialogButton(
            onConfirm = { time ->
                onAction(MeetingCreateAction.OnStartTimeSelected(time))
            },
        ) { openDialog ->
            SoomjaeTextButton(
                onClick = openDialog,
                content = {
                    Text(text = startTime.format(timeFormatter))
                },
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RowScope.EndDateTimeSelection(
    onAction: (MeetingCreateAction) -> Unit,
    timeZone: TimeZone,
    startDate: LocalDate,
    endDate: LocalDate?,
    dateFormatter: DateTimeFormat<LocalDate>,
    endTime: LocalTime?,
    timeFormatter: DateTimeFormat<LocalTime>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatePickerDialogButton(
            onConfirm = { dateInMillis ->
                onAction(MeetingCreateAction.OnEndDateSelected(dateInMillis.toLocalDate(timeZone)))
            },
            selectableDates = DatePickerDefaults.rememberFutureDates(startDate = startDate),
        ) { openDialog ->
            SoomjaeTextButton(
                onClick = openDialog,
                content = {
                    Text(text = endDate?.format(dateFormatter) ?: Res.string.meeting_create_datetime_unselected.value)
                },
            )
        }

        TimePickerDialogButton(
            onConfirm = { time ->
                onAction(MeetingCreateAction.OnEndTimeSelected(time))
            },
        ) { openDialog ->
            SoomjaeTextButton(
                onClick = openDialog,
                content = {
                    Text(text = endTime?.format(timeFormatter) ?: Res.string.meeting_create_datetime_unselected.value)
                },
            )
        }
    }
}

@Composable
private fun MeetingParticipantCount(
    maxParticipantCount: Int?,
    modifier: Modifier = Modifier,
) {
    WriteSelectionButton(
        label = Res.string.meeting_create_participant_count_label.value,
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        buttonText = {
            if (maxParticipantCount == null) {
                Text(text = Res.string.meeting_create_participant_count_display_unlimited.value())
            } else {
                Text(text = Res.string.meeting_create_participant_count_display.value(maxParticipantCount))
            }
        },
        onClick = { /* TODO: Handle participant count selection */ },
    )
}

@OptIn(ExperimentalTime::class)
private fun Long.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(timeZone)
    .date

@Preview
@Composable
private fun MeetingCreateScreenPreview() {
    AppTheme {
        MeetingCreateScreen(
            state = MeetingCreateState(),
            onAction = { },
        )
    }
}
