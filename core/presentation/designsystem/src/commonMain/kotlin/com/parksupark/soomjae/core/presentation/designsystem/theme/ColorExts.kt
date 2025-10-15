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

val SoomjaeColors.errorContainer: Color
    @Composable get() = extendedColor(
        light = Red100,
        dark = Red100,
    )

val SoomjaeColors.onErrorContainer: Color
    @Composable get() = extendedColor(
        light = Red500,
        dark = Red500,
    )

val SoomjaeColors.warningContainer: Color
    @Composable get() = extendedColor(
        light = Orange100,
        dark = Orange100,
    )

val SoomjaeColors.onWarningContainer: Color
    @Composable get() {
        return extendedColor(
            light = Orange600,
            dark = Orange600,
        )
    }

val SoomjaeColors.successContainer: Color
    @Composable get() = extendedColor(
        light = Green100,
        dark = Green100,
    )

val SoomjaeColors.onSuccessContainer: Color
    @Composable get() = extendedColor(
        light = Green600,
        dark = Green600,
    )

val SoomjaeColors.infoContainer: Color
    @Composable get() = extendedColor(
        light = Blue100,
        dark = Blue100,
    )

val SoomjaeColors.onInfoContainer: Color
    @Composable get() = extendedColor(
        light = Blue600,
        dark = Blue600,
    )
