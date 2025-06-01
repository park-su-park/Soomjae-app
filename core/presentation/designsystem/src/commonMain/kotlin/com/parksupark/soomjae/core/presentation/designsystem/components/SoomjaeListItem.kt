package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors = SoomjaeListItemDefaults.colors,
    tonalElevation: Dp = SoomjaeListItemDefaults.elevation,
    shadowElevation: Dp = SoomjaeListItemDefaults.shadowElevation,
) {
    ListItem(
        headlineContent = headlineContent,
        modifier = modifier,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    )
}

object SoomjaeListItemDefaults {
    val colors: ListItemColors
        @Composable get() = ListItemColors(
            containerColor = Color.Transparent,
            headlineColor = SoomjaeTheme.colorScheme.text1,
            leadingIconColor = SoomjaeTheme.colorScheme.icon,
            overlineColor = SoomjaeTheme.colorScheme.text2,
            supportingTextColor = SoomjaeTheme.colorScheme.text2,
            trailingIconColor = SoomjaeTheme.colorScheme.icon,
            disabledHeadlineColor = SoomjaeTheme.colorScheme.text1,
            disabledLeadingIconColor = SoomjaeTheme.colorScheme.iconDisabled,
            disabledTrailingIconColor = SoomjaeTheme.colorScheme.iconDisabled,
        )

    val elevation: Dp
        @Composable get() = ListItemDefaults.Elevation

    val shadowElevation: Dp
        @Composable get() = ListItemDefaults.Elevation
}
