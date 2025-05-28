package com.parksupark.soomjae.core.presentation.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

private val LocalSoomjaeColors = staticCompositionLocalOf<SoomjaeColors> {
    error("No LocalSoomjaeColors provided")
}

private val LocalSoomjaeTypography = staticCompositionLocalOf<SoomjaeTypography> {
    error("No LocalSoomjaeTypography provided")
}

object SoomjaeTheme {
    val colorScheme: SoomjaeColors
        @Composable
        get() = LocalSoomjaeColors.current
    val typography: SoomjaeTypography
        @Composable
        get() = LocalSoomjaeTypography.current
}

@Composable
fun ProvideSoomjaeColorsAndTypography(
    colors: SoomjaeColors,
    typography: SoomjaeTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        value = LocalSoomjaeColors provides colors,
    ) {
        CompositionLocalProvider(
            LocalSoomjaeTypography provides typography,
            content = content,
        )
    }
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val isDarkState = remember(darkTheme) { mutableStateOf(darkTheme) }

    ProvideSoomjaeColorsAndTypography(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = SoomjaeTypography,
    ) {
        CompositionLocalProvider(
            LocalThemeIsDark provides isDarkState,
        ) {
            val isDark by isDarkState
            SystemAppearance(!isDark)

            MaterialTheme(
                colorScheme = debugColorScheme(darkTheme),
                content = { Surface(content = content) },
            )
        }
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
