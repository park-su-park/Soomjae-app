package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.components

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeDatePicker
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeDatePickerDefaults
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_cancel
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_datetime_dialog_confirm

@Composable
internal fun DatePickerDialogButton(
    onConfirm: (dateInMillis: Long) -> Unit,
    modifier: Modifier = Modifier.Companion,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    button: @Composable (openDialog: () -> Unit) -> Unit,
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates,
    )

    if (isDialogOpen) {
        DatePickerDialog(
            onDismissRequest = { /* no-op */ },
            confirmButton = {
                SoomjaeTextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onConfirm(it) }
                        isDialogOpen = false
                    },
                    enabled = datePickerState.selectedDateMillis != null,
                    content = {
                        Text(text = Res.string.meeting_create_datetime_dialog_confirm.value)
                    },
                )
            },
            modifier = modifier,
            dismissButton = {
                SoomjaeTextButton(
                    onClick = { isDialogOpen = false },
                    content = {
                        Text(text = Res.string.meeting_create_datetime_dialog_cancel.value)
                    },
                )
            },
            colors = SoomjaeDatePickerDefaults.colors(),
        ) {
            SoomjaeDatePicker(state = datePickerState)
        }
    }

    button { isDialogOpen = true }
}
