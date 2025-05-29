package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SoomjaeSnackbarDefaults.color,
    contentColor: Color = SoomjaeSnackbarDefaults.contentColor,
    actionColor: Color = SoomjaeSnackbarDefaults.actionColor,
    actionContentColor: Color = SoomjaeSnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SoomjaeSnackbarDefaults.dismissActionContentColor,
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
    )
}

@Composable
fun SoomjaeErrorSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SoomjaeErrorSnackbarDefaults.color,
    contentColor: Color = SoomjaeErrorSnackbarDefaults.contentColor,
    actionColor: Color = SoomjaeErrorSnackbarDefaults.actionColor,
    actionContentColor: Color = SoomjaeErrorSnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SoomjaeErrorSnackbarDefaults.dismissActionContentColor,
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
    )
}

object SoomjaeSnackbarDefaults {
    val color: Color
        @Composable get() = SoomjaeTheme.colorScheme.text1

    val contentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background2

    val actionColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background2

    val actionContentColor: Color
        @Composable get() = Color.Transparent

    val dismissActionContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background2
}

object SoomjaeErrorSnackbarDefaults {
    val color: Color
        @Composable get() = SoomjaeTheme.colorScheme.error

    val contentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.text1W

    val actionColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.text1W

    val actionContentColor: Color
        @Composable get() = Color.Transparent

    val dismissActionContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.iconW
}
