package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TimePickerDialog as MaterialTimePickerDialog

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    modeToggleButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
//    shape: Shape = TimePickerDialogDefaults.shape,
//    containerColor: Color = TimePickerDialogDefaults.containerColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        SoomjaeSurface(
            shape = MaterialTheme.shapes.extraLarge,
            color = SoomjaeTheme.colorScheme.background2,
            elevation = 4.dp,
        ) {
            Column(modifier = modifier.padding(24.dp)) {
                title()

                Spacer(modifier = Modifier.height(16.dp))

                content()

                Row(modifier = Modifier.align(Alignment.End)) {
                    if (modeToggleButton != null) {
                        modeToggleButton()
                    }
                    if (dismissButton != null) {
                        dismissButton()
                    }
                    confirmButton()
                }
            }
        }
    }
}

@Composable
fun SoomjaeTimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    modeToggleButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = SoomjaeTimePickerDialogDefaults.shape,
    containerColor: Color = SoomjaeTimePickerDialogDefaults.containerColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    MaterialTimePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        title = title,
        modifier = modifier,
        properties = properties,
        modeToggleButton = modeToggleButton,
        dismissButton = dismissButton,
        shape = shape,
        containerColor = containerColor,
        content = content,
    )
}

object SoomjaeTimePickerDialogDefaults {
    val containerColor
        @Composable get() = SoomjaeTheme.colorScheme.background1

    val shape
        @Composable get() = TimePickerDialogDefaults.shape
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimePickerDialogPreview() {
    AppTheme {
        TimePickerDialog(
            onDismissRequest = {},
            confirmButton = { Text("Confirm") },
            title = { Text("Select Time") },
            content = {
                SoomjaeTimePicker(
                    state = rememberTimePickerState(),
                    modifier = Modifier,
                )
            },
        )
    }
}
