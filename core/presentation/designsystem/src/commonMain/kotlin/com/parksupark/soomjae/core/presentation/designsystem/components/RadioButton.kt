package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = SoomjaeRadioButtonDefaults.colors,
    interactionSource: MutableInteractionSource? = null,
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource ?: MutableInteractionSource(),
    )
}

object SoomjaeRadioButtonDefaults {
    val colors: RadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors(
            selectedColor = SoomjaeTheme.colorScheme.cta,
            unselectedColor = SoomjaeTheme.colorScheme.text2,
            disabledSelectedColor = SoomjaeTheme.colorScheme.cta.copy(alpha = 0.4f),
            disabledUnselectedColor = SoomjaeTheme.colorScheme.text2.copy(alpha = 0.4f),
        )
}
