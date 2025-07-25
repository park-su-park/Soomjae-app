package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeSecureOutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    title: String? = null,
    startIcon: ImageVector? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Companion.Text,
    additionalInfo: String? = null,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    SoomjaeSurface(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically,
            ) {
                ProvideTextStyle(SoomjaeTheme.typography.captionS) {
                    if (title != null) {
                        Text(
                            text = title,
                            color = SoomjaeTheme.colorScheme.text2,
                        )
                    }
                    if (error != null) {
                        Text(
                            text = error,
                            color = SoomjaeTheme.colorScheme.error,
                        )
                    } else if (additionalInfo != null) {
                        Text(
                            text = additionalInfo,
                            color = SoomjaeTheme.colorScheme.text2,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            BasicSecureTextField(
                modifier = Modifier.clip(MaterialTheme.shapes.small)
                    .border(
                        width = 1.dp,
                        color = when {
                            isFocused -> SoomjaeTheme.colorScheme.divider2

                            error != null -> SoomjaeTheme.colorScheme.error

                            else -> SoomjaeTheme.colorScheme.divider1
                        },
                        shape = MaterialTheme.shapes.small,
                    )
                    .padding(8.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                state = state,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                decorator = {
                    SecureOutlinedTextFieldDecorator(
                        startIcon = startIcon,
                        state = state,
                        isFocused = isFocused,
                        hint = hint,
                        isPasswordVisible = isPasswordVisible,
                        onVisibilityToggle = { isPasswordVisible = !isPasswordVisible },
                        innerBox = it,
                    )
                },
                textObfuscationMode = getTextObfuscationMode(isPasswordVisible),
            )
        }
    }
}

@Composable
private fun SecureOutlinedTextFieldDecorator(
    startIcon: ImageVector?,
    state: TextFieldState,
    isFocused: Boolean,
    hint: String,
    isPasswordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    innerBox: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Companion.CenterVertically,
    ) {
        if (startIcon != null) {
            Icon(
                imageVector = startIcon,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.icon,
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Box(
            modifier = Modifier.weight(1f).heightIn(min = 24.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (state.text.isEmpty() && !isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = hint,
                    color = SoomjaeTheme.colorScheme.text4,
                    style = SoomjaeTheme.typography.captionL,
                )
            }
            innerBox()
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = null,
            tint = SoomjaeTheme.colorScheme.icon.copy(
                alpha = 0.6f,
            ),
            modifier = Modifier.clip(MaterialTheme.shapes.large)
                .clickable {
                    onVisibilityToggle()
                },
        )
    }
}

private fun getTextObfuscationMode(passwordVisible: Boolean): TextObfuscationMode = if (passwordVisible) {
    TextObfuscationMode.Visible
} else {
    TextObfuscationMode.RevealLastTyped
}
