package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeColors
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SoomjaeFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = FilterChipDefaults.shape,
    colors: SelectableChipColors = SoomjaeFilterChipDefaults.filterChipColors(),
    elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
    border: BorderStroke? = SoomjaeFilterChipDefaults.filterChipBorder(enabled, selected),
    interactionSource: MutableInteractionSource? = null,
) {
    ProvideTextStyle(SoomjaeTheme.typography.button2) {
        FilterChip(
            selected = selected,
            onClick = onClick,
            label = label,
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            interactionSource = interactionSource,
        )
    }
}

object SoomjaeFilterChipDefaults {
    internal val SoomjaeColors.defaultFilterChipColors: SelectableChipColors
        @Composable get() = defaultFilterChipColorsCached ?: FilterChipDefaults.filterChipColors(
            containerColor = SoomjaeTheme.colorScheme.background1,
            labelColor = SoomjaeTheme.colorScheme.text2,
            iconColor = SoomjaeTheme.colorScheme.icon,
            disabledContainerColor = SoomjaeTheme.colorScheme.background2,
            disabledLabelColor = SoomjaeTheme.colorScheme.text4,
            disabledLeadingIconColor = SoomjaeTheme.colorScheme.iconDisabled,
            disabledTrailingIconColor = SoomjaeTheme.colorScheme.iconDisabled,
            selectedContainerColor = SoomjaeTheme.colorScheme.ctaSecondary,
            disabledSelectedContainerColor = SoomjaeTheme.colorScheme.background2,
            selectedLabelColor = SoomjaeTheme.colorScheme.primary,
            selectedLeadingIconColor = SoomjaeTheme.colorScheme.iconColored,
            selectedTrailingIconColor = SoomjaeTheme.colorScheme.iconColored,
        ).also {
            defaultFilterChipColorsCached = it
        }

    @Composable
    fun filterChipColors() = SoomjaeTheme.colorScheme.defaultFilterChipColors

    @Composable
    fun filterChipBorder(
        enabled: Boolean,
        selected: Boolean,
    ): BorderStroke = FilterChipDefaults.filterChipBorder(
        enabled = enabled,
        selected = selected,
        borderColor = SoomjaeTheme.colorScheme.divider1,
        selectedBorderColor = SoomjaeTheme.colorScheme.primary,
        disabledBorderColor = SoomjaeTheme.colorScheme.divider1,
        disabledSelectedBorderColor = SoomjaeTheme.colorScheme.divider2,
    )
}

@Preview
@Composable
private fun SoomjaeFilterChipPreview() {
    AppTheme {
        SoomjaeSurface {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SoomjaeFilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("chip") },
                )
                SoomjaeFilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("selected") },
                )
            }
        }
    }
}

@Preview
@Composable
private fun SoomjaeFilterChipDisabledPreview() {
    AppTheme {
        SoomjaeSurface {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SoomjaeFilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("disabled") },
                    enabled = false,
                )
                SoomjaeFilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("disabledSelected") },
                    enabled = false,
                )
            }
        }
    }
}
