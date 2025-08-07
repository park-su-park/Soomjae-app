package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTimePicker
import com.parksupark.soomjae.core.presentation.designsystem.components.TimePickerDialog
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_cancel
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_confirm
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerDialogButton(
    onConfirm: (time: LocalTime) -> Unit,
    modifier: Modifier = Modifier.Companion,
    button: @Composable (openDialog: () -> Unit) -> Unit,
) {
    val timePickerState = rememberTimePickerState()
    var dialogOpen by remember { mutableStateOf(false) }

    if (dialogOpen) {
        TimePickerDialog(
            onDismissRequest = { /* no-op */ },
            confirmButton = {
                SoomjaeTextButton(
                    onClick = {
                        onConfirm(
                            LocalTime(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute,
                            ),
                        )
                        dialogOpen = false
                    },
                    content = { Text(text = Res.string.meeting_create_datetime_dialog_confirm.value) },
                )
            },
            title = { },
            modifier = modifier,
            dismissButton = {
                SoomjaeTextButton(
                    onClick = { dialogOpen = false },
                    content = { Text(text = Res.string.meeting_create_datetime_dialog_cancel.value) },
                )
            },
        ) {
            SoomjaeTimePicker(timePickerState)
        }
    }

    button { dialogOpen = true }
}
