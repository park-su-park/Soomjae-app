package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.conditional
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.comment_bar_input_placeholder
import com.parksupark.soomjae.features.posts.common.presentation.resources.comment_bar_send_button
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommentInputBar(
    state: TextFieldState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    canSubmit: Boolean = true,
    isLoggedIn: Boolean = true,
    onLoginRequest: () -> Unit = { },
) {
    val canSend by remember(canSubmit, state.text) {
        derivedStateOf { canSubmit && state.text.isNotBlank() }
    }

    Row(
        modifier = modifier
            .background(
                color = SoomjaeTheme.colorScheme.background2,
                shape = MaterialTheme.shapes.medium.copy(
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp),
                ),
            ).padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            CommentTextField(
                state = state,
                isLoggedIn = isLoggedIn,
                onRequireLogin = onLoginRequest,
                modifier = Modifier.fillMaxWidth()
                    .focusProperties { canFocus = isLoggedIn },
            )

            if (!isLoggedIn) {
                val description = Res.string.comment_bar_input_placeholder.value
                Box(
                    modifier = Modifier.matchParentSize().clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onLoginRequest,
                    ).semantics(mergeDescendants = true) {
                        role = Role.Button
                        contentDescription = description
                    },
                )
            }
        }

        SendButton(
            enabled = canSend,
            onClick = onSendClick,
            modifier = Modifier.align(Alignment.Bottom),
        )
    }
}

@Composable
private fun CommentTextField(
    state: TextFieldState,
    isLoggedIn: Boolean,
    onRequireLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        state = state,
        modifier = modifier.conditional(
            isLoggedIn,
            ifTrue = { onFocusChanged { isFocused = it.isFocused } },
            ifFalse = { clickable(onClick = onRequireLogin) },
        ),
        textStyle = SoomjaeTheme.typography.body2.copy(
            color = SoomjaeTheme.colorScheme.text2,
        ),
        lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 4),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.padding(8.dp),
            ) {
                if (state.text.isEmpty() && !isFocused) {
                    PlaceholderText(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun PlaceholderText(modifier: Modifier = Modifier) {
    Text(
        text = Res.string.comment_bar_input_placeholder.value,
        modifier = modifier,
        style = SoomjaeTheme.typography.body2.copy(
            color = SoomjaeTheme.colorScheme.text4,
        ),
    )
}

@Composable
private fun SendButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = Res.string.comment_bar_send_button.value,
            tint = if (enabled) {
                SoomjaeTheme.colorScheme.iconColored
            } else {
                SoomjaeTheme.colorScheme.iconDisabled
            },
        )
    }
}

@Preview
@Composable
private fun CommentBarPreview_Empty() {
    AppTheme {
        SoomjaeSurface {
            CommentInputBar(
                state = remember { TextFieldState() },
                onSendClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun CommentBarPreview_Empty_Dark() {
    AppTheme(isSystemDarkTheme = true) {
        SoomjaeSurface {
            CommentInputBar(
                state = remember { TextFieldState() },
                onSendClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun CommentBarPreview_Filled() {
    AppTheme {
        SoomjaeSurface {
            CommentInputBar(
                state = remember { TextFieldState("너무너무 재밌어요!") },
                onSendClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun CommentBarPreview_Filled_Dark() {
    AppTheme(isSystemDarkTheme = true) {
        SoomjaeSurface {
            CommentInputBar(
                state = remember { TextFieldState("너무너무 재밌어요!") },
                onSendClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun CommentBarPreview_Filled_Long() {
    AppTheme {
        SoomjaeSurface {
            CommentInputBar(
                state = remember {
                    TextFieldState(
                        "너무너무 재밌어요! ".repeat(25),
                    )
                },
                onSendClick = { },
            )
        }
    }
}
