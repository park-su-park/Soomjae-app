package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow

fun TextFieldState.collectAsFlow() = snapshotFlow { text }
