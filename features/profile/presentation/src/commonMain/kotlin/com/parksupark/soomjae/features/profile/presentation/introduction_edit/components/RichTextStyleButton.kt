package com.parksupark.soomjae.features.profile.presentation.introduction_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RichTextStyleButton(
    onClick: () -> Unit,
    icon: ImageVector,
    tint: Color? = null,
    isSelected: Boolean = false,
) {
    IconButton(
        modifier = Modifier
            .focusProperties { canFocus = false },
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (isSelected) {
                SoomjaeTheme.colorScheme.ctaSecondaryText
            } else {
                SoomjaeTheme.colorScheme.text1
            },
        ),
    ) {
        Icon(
            icon,
            contentDescription = icon.name,
            tint = tint ?: LocalContentColor.current,
            modifier = Modifier
                .background(
                    color = if (isSelected) {
                        SoomjaeTheme.colorScheme.ctaSecondary
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RichTextStyleButtonPreview() {
    AppTheme {
        RichTextStyleButton(
            onClick = { },
            icon = Icons.Default.Add,
            isSelected = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RichTextStyleButton_SelectedPreview() {
    AppTheme {
        RichTextStyleButton(
            onClick = { },
            icon = Icons.Default.Add,
            isSelected = true,
        )
    }
}
