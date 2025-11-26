package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

fun TextFieldState.collectAsFlow() = snapshotFlow { text }

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T> StateFlow<T>.mapTextFieldState(
    crossinline selector: (T) -> TextFieldState,
): Flow<CharSequence> = this
    .map(selector)
    .distinctUntilChanged()
    .flatMapLatest { it.collectAsFlow() }
    .distinctUntilChanged()
