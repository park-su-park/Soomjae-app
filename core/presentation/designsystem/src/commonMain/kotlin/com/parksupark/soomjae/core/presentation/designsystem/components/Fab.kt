package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = SoomjaeFloatingActionButtonDefaults.shape,
    containerColor: Color = SoomjaeFloatingActionButtonDefaults.containerColor,
    contentColor: Color = SoomjaeFloatingActionButtonDefaults.contentColor,
    elevation: FloatingActionButtonElevation = SoomjaeFloatingActionButtonDefaults.elevation,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content,
    )
}

object SoomjaeFloatingActionButtonDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(56.dp)

    val containerColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.iconW

    val contentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.cta

    val elevation: FloatingActionButtonElevation
        @Composable get() = FloatingActionButtonDefaults.elevation()
}
