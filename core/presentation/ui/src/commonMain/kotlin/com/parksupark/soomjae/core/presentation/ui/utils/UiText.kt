package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource as ComposeStringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        val resource: ComposeStringResource,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resource, *args)
    }
}
