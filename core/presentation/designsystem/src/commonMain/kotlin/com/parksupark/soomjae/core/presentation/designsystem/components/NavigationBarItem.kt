package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun RowScope.SoomjaeNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = SoomjaeNavigationBarItemColors,
    interactionSource: MutableInteractionSource? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource,
    )
}

private val SoomjaeNavigationBarItemColors: NavigationBarItemColors
    @Composable
    get() = NavigationBarItemColors(
        selectedIconColor = SoomjaeTheme.colorScheme.primary,
        selectedTextColor = SoomjaeTheme.colorScheme.primary,
        selectedIndicatorColor = Color.Unspecified,
        unselectedIconColor = SoomjaeTheme.colorScheme.text2,
        unselectedTextColor = SoomjaeTheme.colorScheme.text2,
        disabledIconColor = SoomjaeTheme.colorScheme.text2.copy(alpha = 0.38f),
        disabledTextColor = SoomjaeTheme.colorScheme.text2.copy(alpha = 0.38f),
    )
