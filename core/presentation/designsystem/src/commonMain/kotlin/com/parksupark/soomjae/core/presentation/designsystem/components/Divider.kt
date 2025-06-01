package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = SoomjaeDividerDefaults.thickness,
    color: Color = SoomjaeDividerDefaults.color,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}

object SoomjaeDividerDefaults {
    val thickness: Dp
        @Composable
        get() = DividerDefaults.Thickness
    val color: Color
        @Composable
        get() = SoomjaeTheme.colorScheme.divider1
}
