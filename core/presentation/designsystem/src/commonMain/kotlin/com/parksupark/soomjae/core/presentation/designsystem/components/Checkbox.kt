package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

object SoomjaeCheckboxDefaults {
    private const val DISABLED_CONTENT_OPACITY = 0.38f

    @Composable
    fun colors(
        checkedColor: Color = SoomjaeTheme.colorScheme.primary,
        uncheckedColor: Color = SoomjaeTheme.colorScheme.background2,
        checkmarkColor: Color = SoomjaeTheme.colorScheme.iconW,
        disabledCheckedColor: Color = SoomjaeTheme.colorScheme.primary.copy(alpha = DISABLED_CONTENT_OPACITY),
        disabledUncheckedColor: Color = SoomjaeTheme.colorScheme.background2.copy(alpha = DISABLED_CONTENT_OPACITY),
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
