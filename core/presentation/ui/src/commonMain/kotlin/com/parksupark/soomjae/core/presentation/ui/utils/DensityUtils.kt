package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextUnit.toDp(): Dp = with(LocalDensity.current) {
    toDp()
}

@Composable
fun Int.pxToDp(): Dp = with(LocalDensity.current) {
    this@pxToDp.toDp()
}
