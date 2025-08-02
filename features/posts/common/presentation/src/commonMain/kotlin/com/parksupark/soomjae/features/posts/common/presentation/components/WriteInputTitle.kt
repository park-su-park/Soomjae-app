package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.write_input_title_placeholder

@Composable
fun WriteInputTitle(
    state: TextFieldState,
    hint: String = Res.string.write_input_title_placeholder.value,
    modifier: Modifier = Modifier,
) {
    ProvideTextStyle(SoomjaeTheme.typography.title2) {
        BasicTextField(
            state = state,
            modifier = modifier.padding(horizontal = 16.dp),
            textStyle = LocalTextStyle.current.copy(
                color = SoomjaeTheme.colorScheme.text1,
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(SoomjaeTheme.colorScheme.cta),
            decorator = { innerBox ->
                if (state.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = SoomjaeTheme.colorScheme.text4,
                    )
                }
                CompositionLocalProvider(
                    LocalTextSelectionColors provides TextSelectionColors(
                        handleColor = SoomjaeTheme.colorScheme.cta,
                        backgroundColor = SoomjaeTheme.colorScheme.cta.copy(alpha = 0.4f),
                    ),
                ) {
                    innerBox()
                }
            },
        )
    }
}
