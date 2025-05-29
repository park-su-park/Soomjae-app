package com.parksupark.soomjae.core.presentation.ui.components

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeErrorSnackbar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSnackbar
import com.parksupark.soomjae.core.presentation.ui.utils.UiText

@Composable
fun SoomjaeSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
    ) { data ->
        val soomjaeSnackbarVisuals = data.visuals as? SoomjaeSnackbarVisuals
        val visuals = soomjaeSnackbarVisuals?.toSnackbarVisuals() ?: data.visuals
        val snackbarData = SoomjaeSnackbarData(data, visuals)

        if (soomjaeSnackbarVisuals?.isError == true) {
            SoomjaeErrorSnackbar(snackbarData)
        } else {
            SoomjaeSnackbar(snackbarData)
        }
    }
}

suspend fun SnackbarHostState.showSnackbar(
    uiText: UiText,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = if (actionLabel == null) {
        SnackbarDuration.Short
    } else {
        SnackbarDuration.Indefinite
    },
    isError: Boolean = false,
) = showSnackbar(
    SoomjaeSnackbarVisuals(
        uiText = uiText,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration,
        isError = isError,
    ),
)

private class SoomjaeSnackbarVisuals(
    val uiText: UiText,
    override val actionLabel: String?,
    override val withDismissAction: Boolean,
    override val duration: SnackbarDuration,
    val isError: Boolean,
) : SnackbarVisuals {
    override val message: String = ""
}

@Composable
private fun SoomjaeSnackbarVisuals.toSnackbarVisuals(): SnackbarVisuals = let { visuals ->
    object : SnackbarVisuals {
        override val message: String = uiText.asString()
        override val withDismissAction: Boolean = visuals.withDismissAction
        override val actionLabel: String? = visuals.actionLabel
        override val duration: SnackbarDuration = visuals.duration
    }
}

class SoomjaeSnackbarData(
    data: SnackbarData,
    override val visuals: SnackbarVisuals,
) : SnackbarData by data
