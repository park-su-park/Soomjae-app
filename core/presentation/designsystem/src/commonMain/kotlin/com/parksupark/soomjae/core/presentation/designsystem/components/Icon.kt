package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = SoomjaeTheme.colorScheme.icon,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}
