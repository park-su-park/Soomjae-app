package com.parksupark.soomjae.core.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
private fun extendedColor(
    light: Color,
    dark: Color,
): Color = if (LocalThemeIsDark.current.value) {
    dark
} else {
    light
}

val SoomjaeColors.like: Color
    @Composable get() = extendedColor(
        light = Red400,
        dark = Red200,
    )

val SoomjaeColors.comment: Color
    @Composable get() = extendedColor(
        light = Green400,
        dark = Green200,
    )
