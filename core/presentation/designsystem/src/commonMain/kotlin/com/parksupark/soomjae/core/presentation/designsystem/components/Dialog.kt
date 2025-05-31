package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = SoomjaeAlertDialogDefaults.shape,
    containerColor: Color = SoomjaeAlertDialogDefaults.containerColor,
    iconContentColor: Color = SoomjaeAlertDialogDefaults.iconContentColor,
    titleContentColor: Color = SoomjaeAlertDialogDefaults.titleContentColor,
    textContentColor: Color = SoomjaeAlertDialogDefaults.textContentColor,
    tonalElevation: Dp = SoomjaeAlertDialogDefaults.tonalElevation,
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}

object SoomjaeAlertDialogDefaults {
    val containerColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background2

    val iconContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.icon

    val titleContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.text1

    val textContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.text2

    val shape: Shape
        @Composable get() = AlertDialogDefaults.shape

    val tonalElevation: Dp
        @Composable get() = AlertDialogDefaults.TonalElevation
}
