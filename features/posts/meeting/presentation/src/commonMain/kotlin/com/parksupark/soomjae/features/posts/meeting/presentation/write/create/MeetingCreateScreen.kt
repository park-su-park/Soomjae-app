package com.parksupark.soomjae.features.posts.meeting.presentation.write.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeOutlinedTextField
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.rememberFutureDates
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionLayout
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components.DatePickerDialogButton
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components.TimePickerDialogButton
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreateUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_button_create_meeting
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_unselected
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_navigate_up_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_additional_info_unlimited
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
                meeting = state.meeting,
                onAction = onAction,
            )

            MeetingParticipantCount(state = state.meeting.inputMaxParticipantCount)
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
    meeting: MeetingCreateUi,
    onAction: (MeetingCreateAction) -> Unit,
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
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            StartDateTimeSelection(
                onAction = onAction,
                timeZone = timeZone,
                startDate = meeting.startDate,
                dateFormatter = dateFormatter,
                startTime = meeting.startTime,
                timeFormatter = timeFormatter,
            )

            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)

            EndDateTimeSelection(
                onAction = onAction,
                timeZone = timeZone,
                startDate = meeting.startDate,
                endDate = meeting.endDate,
                dateFormatter = dateFormatter,
                endTime = meeting.endTime,
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
    state: TextFieldState,
    modifier: Modifier = Modifier,
) {
    WriteSelectionLayout(
        label = Res.string.meeting_create_participant_count_label.value,
        modifier = modifier.fillMaxWidth(),
    ) {
        val participantUnlimitedText = Res.string.meeting_create_participant_count_display_unlimited.value
        val participantCountText = Res.string.meeting_create_participant_count_display.value

        var isFocused by remember { mutableStateOf(false) }

        if (isFocused) {
            LaunchedEffect(Unit) {
                state.edit {
                    selection = TextRange(0, originalText.length)
                }
            }
        }

        SoomjaeOutlinedTextField(
            state = state,
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            startIcon = Icons.Outlined.Person,
            keyboardType = KeyboardType.Number,
            inputTransformation = {
                val newText = originalText.filter { it.isDigit() }
                if (newText.length != originalText.length) {
                    replace(0, originalText.length, newText)
                }
            },
            outputTransformation = {
                if (!isFocused) {
                    val num = originalText.toString().toLongOrNull() ?: -1

                    val output = if (num == 0L) {
                        participantUnlimitedText
                    } else if (num > 0L) {
                        participantCountText.replace("%1\$d", num.toString())
                    } else {
                        originalText
                    }

                    replace(
                        start = 0,
                        end = originalText.length,
                        text = output,
                    )
                }
            },
            additionalInfo = Res.string.meeting_create_participant_count_additional_info_unlimited.value,
        )
    }
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
