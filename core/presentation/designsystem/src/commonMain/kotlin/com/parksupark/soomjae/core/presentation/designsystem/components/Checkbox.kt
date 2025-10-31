package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = SoomjaeCheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
    )
}

@Composable
fun SoomjaeCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = SoomjaeCheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource? = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null,
            role = Role.Checkbox,
            onClick = if (onCheckedChange != null) {
                { onCheckedChange(!checked) }
            } else {
                { }
            },
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SoomjaeCheckbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
        )

        Spacer(Modifier.width(4.dp))

        ProvideTextStyle(
            value = SoomjaeTheme.typography.button1,
            content = content,
        )
    }
}

object SoomjaeCheckboxDefaults {
    private const val DISABLED_CONTENT_OPACITY = 0.38f

    @Composable
    fun colors(
        checkedColor: Color = SoomjaeTheme.colorScheme.primary,
        uncheckedColor: Color = SoomjaeTheme.colorScheme.icon,
        checkmarkColor: Color = SoomjaeTheme.colorScheme.iconW,
        disabledCheckedColor: Color = SoomjaeTheme.colorScheme.primary.copy(
            alpha = DISABLED_CONTENT_OPACITY,
        ),
        disabledUncheckedColor: Color = uncheckedColor.copy(
            alpha = DISABLED_CONTENT_OPACITY,
        ),
        disabledIndeterminateColor: Color = checkedColor.copy(alpha = DISABLED_CONTENT_OPACITY),
    ): CheckboxColors = CheckboxDefaults.colors(
        checkedColor = checkedColor,
        uncheckedColor = uncheckedColor,
        checkmarkColor = checkmarkColor,
        disabledCheckedColor = disabledCheckedColor,
        disabledUncheckedColor = disabledUncheckedColor,
        disabledIndeterminateColor = disabledIndeterminateColor,
    )
}
