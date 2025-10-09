package com.parksupark.soomjae.core.presentation.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.parksupark.soomjae.core.common.theme.ColorTheme

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
    val drawable: SoomjaeDrawable
        @Composable
        get() = SoomjaeDrawable
}

@Composable
fun ProvideSoomjaeColorsAndTypography(
    colors: SoomjaeColors,
    typography: SoomjaeTypography,
    content: @Composable () -> Unit,
) {
    val textSelectionColors = TextSelectionColors(
        handleColor = colors.primary,
        backgroundColor = colors.primary.copy(alpha = 0.4f),
    )

    CompositionLocalProvider(
        LocalSoomjaeColors provides colors,
        LocalSoomjaeTypography provides typography,
        LocalTextSelectionColors provides textSelectionColors,
        content = content,
    )
}

@Composable
fun AppTheme(
    theme: ColorTheme = ColorTheme.SYSTEM,
    isSystemDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val isDarkState = remember(theme) {
        mutableStateOf(
            when (theme) {
                ColorTheme.SYSTEM -> isSystemDarkTheme
                ColorTheme.LIGHT -> false
                ColorTheme.DARK -> true
            },
        )
    }

    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState,
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)

        MaterialTheme(
            colorScheme = debugColorScheme(isDarkState.value),
        ) {
            ProvideSoomjaeColorsAndTypography(
                colors = if (isDarkState.value) DarkColorPalette else LightColorPalette,
                typography = SoomjaeTypography,
                content = content,
            )
        }
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
