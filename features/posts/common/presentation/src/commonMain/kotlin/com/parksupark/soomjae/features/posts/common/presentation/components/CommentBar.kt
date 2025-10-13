package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.comment_bar_input_placeholder
import com.parksupark.soomjae.features.posts.common.presentation.resources.comment_bar_send_button
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommentBar(
    state: TextFieldState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(
                color = SoomjaeTheme.colorScheme.background2,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            state = state,
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                ) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = Res.string.comment_bar_input_placeholder.value,
                            style = SoomjaeTheme.typography.body2.copy(
                                color = SoomjaeTheme.colorScheme.text3,
                            ),
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.weight(1f).onFocusChanged {
                isFocused = it.isFocused
            },
            textStyle = SoomjaeTheme.typography.body2,
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 4),
        )

        IconButton(
            onClick = onSendClick,
            content = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    tint = SoomjaeTheme.colorScheme.iconColored,
                    contentDescription = Res.string.comment_bar_send_button.value,
                )
            },
        )
    }
}

@Preview
@Composable
private fun CommentBarPreview_Empty() {
    AppTheme {
        SoomjaeSurface {
            CommentBar(
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
            CommentBar(
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
            CommentBar(
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
