package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun SoomjaeTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    title: String? = null,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    endContent: (@Composable (() -> Unit))? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = SoomjaeTheme.typography.labelS.copy(
                        color = SoomjaeTheme.colorScheme.text2,
                    ),
                )
            }
            if (error != null) {
                Text(
                    text = error,
                    style = SoomjaeTheme.typography.labelS.copy(
                        color = SoomjaeTheme.colorScheme.error,
                    ),
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    style = SoomjaeTheme.typography.labelS.copy(
                        color = SoomjaeTheme.colorScheme.text2,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        SoomjaeBasicTextField(
            state = state,
            enabled = enabled,
            textStyle = SoomjaeTheme.typography.captionL.copy(
                color = SoomjaeTheme.colorScheme.text1,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(SoomjaeTheme.colorScheme.text1),
            modifier = Modifier.heightIn(min = 40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        SoomjaeTheme.colorScheme.background2
                    } else {
                        SoomjaeTheme.colorScheme.background1
                    },
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        SoomjaeTheme.colorScheme.divider1
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                OutlinedTextFieldDecorator(
                    startIcon = startIcon,
                    state = state,
                    isFocused = isFocused,
                    hint = hint,
                    endIcon = endIcon,
                    endContent = endContent,
                    innerBox = innerBox,
                )
            },
        )

        Spacer(
            modifier = Modifier.height(1.dp).fillMaxWidth().background(
                color = if (!isFocused) {
                    SoomjaeTheme.colorScheme.divider1
                } else {
                    Color.Transparent
                },
            ),
        )
    }
}

@Composable
fun SoomjaeOutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    title: String? = null,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }

    SoomjaeSurface(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
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
            SoomjaeBasicTextField(
                state = state,
                modifier = Modifier.heightIn(min = 40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        width = 1.dp,
                        color = when {
                            isFocused -> SoomjaeTheme.colorScheme.divider2

                            error != null -> SoomjaeTheme.colorScheme.error

                            else -> SoomjaeTheme.colorScheme.divider1
                        },
                        shape = MaterialTheme.shapes.small,
                    )
                    .background(
                        color = if (enabled) {
                            Color.Transparent
                        } else {
                            SoomjaeTheme.colorScheme.background2
                        },
                    )
                    .padding(8.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                enabled = enabled,
                inputTransformation = inputTransformation,
                textStyle = SoomjaeTheme.typography.captionL.copy(
                    color = if (enabled) {
                        SoomjaeTheme.colorScheme.text1
                    } else {
                        SoomjaeTheme.colorScheme.text4
                    },
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                lineLimits = TextFieldLineLimits.SingleLine,
                outputTransformation = outputTransformation,
                decorator = {
                    OutlinedTextFieldDecorator(
                        startIcon = startIcon,
                        state = state,
                        isFocused = isFocused,
                        hint = hint,
                        endIcon = endIcon,
                        innerBox = it,
                    )
                },
            )
        }
    }
}

@Composable
private fun OutlinedTextFieldDecorator(
    startIcon: ImageVector?,
    state: TextFieldState,
    isFocused: Boolean,
    hint: String,
    endIcon: ImageVector?,
    endContent: (@Composable (() -> Unit))? = null,
    innerBox: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (startIcon != null) {
            Icon(
                imageVector = startIcon,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.text2,
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (state.text.isEmpty() && !isFocused) {
                Text(
                    text = hint,
                    style = SoomjaeTheme.typography.labelM.copy(
                        color = SoomjaeTheme.colorScheme.text3,
                    ),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                )
            }
            innerBox()
        }
        if (endContent != null) {
            endContent()
        } else if (endIcon != null) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = endIcon,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.text2,
                modifier = Modifier.padding(end = 8.dp),
            )
        }
    }
}

object SoomjaeTextFieldDefaults {
    val defaultOutlinedTextFieldColors: TextFieldColors
        @Composable get() = TextFieldDefaults.colors(
            focusedContainerColor = SoomjaeTheme.colorScheme.background1,
            unfocusedContainerColor = SoomjaeTheme.colorScheme.background1,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = SoomjaeTheme.colorScheme.error,
            focusedTextColor = SoomjaeTheme.colorScheme.text1,
            unfocusedTextColor = SoomjaeTheme.colorScheme.text1,
            disabledTextColor = SoomjaeTheme.colorScheme.text4,
            errorTextColor = SoomjaeTheme.colorScheme.error,
        )
}
