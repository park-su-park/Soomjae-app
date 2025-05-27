package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion,
    enabled: Boolean = true,
    shape: Shape = SoomjaeButtonDefaults.shape,
    border: BorderStroke? = null,
    background: Color = SoomjaeTheme.colorScheme.cta,
    disabledBackground: Color = SoomjaeTheme.colorScheme.ctaDisabled,
    contentColor: Color = SoomjaeTheme.colorScheme.text1W,
    disabledContentColor: Color = SoomjaeTheme.colorScheme.text4,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contentAlign: Alignment = Alignment.Center,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    SoomjaeSurface(
        shape = shape,
        color = Color.Companion.Transparent,
        contentColor = if (enabled) contentColor else disabledContentColor,
        border = border,
        modifier = modifier
            .clip(shape = shape)
            .background(if (enabled) background else disabledBackground)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Companion.Button,
                interactionSource = interactionSource,
                indication = null,
            ),
        contentAlign = contentAlign,
    ) {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.button2,
        ) {
            Row(
                Modifier.Companion
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight,
                    )
                    .indication(interactionSource = interactionSource, indication = ripple())
                    .padding(paddingValues = contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

@Composable
fun SoomjaeSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion,
    enabled: Boolean = true,
    shape: Shape = SoomjaeButtonDefaults.shape,
    border: BorderStroke? = null,
    background: Color = SoomjaeTheme.colorScheme.ctaSecondary,
    disabledBackground: Color = SoomjaeTheme.colorScheme.ctaDisabled,
    contentColor: Color = SoomjaeTheme.colorScheme.ctaSecondaryText,
    disabledContentColor: Color = SoomjaeTheme.colorScheme.text4,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contentAlign: Alignment = Alignment.Center,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    SoomjaeButton(
        shape = shape,
        enabled = enabled,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        border = border,
        modifier = modifier,
        background = background,
        disabledBackground = disabledBackground,
        onClick = onClick,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        contentAlign = contentAlign,
        content = content,
    )
}

@Composable
fun SoomjaeTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion,
    enabled: Boolean = true,
    shape: Shape = SoomjaeButtonDefaults.shape,
    border: BorderStroke? = null,
    background: Color = Color.Transparent,
    disabledBackground: Color = SoomjaeTheme.colorScheme.ctaDisabled,
    contentColor: Color = SoomjaeTheme.colorScheme.text1,
    disabledContentColor: Color = SoomjaeTheme.colorScheme.text4,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contentAlign: Alignment = Alignment.Center,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    SoomjaeButton(
        shape = shape,
        enabled = enabled,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        border = border,
        modifier = modifier,
        background = background,
        disabledBackground = disabledBackground,
        onClick = onClick,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        contentAlign = contentAlign,
        content = content,
    )
}

object SoomjaeButtonDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(12.dp)
}
