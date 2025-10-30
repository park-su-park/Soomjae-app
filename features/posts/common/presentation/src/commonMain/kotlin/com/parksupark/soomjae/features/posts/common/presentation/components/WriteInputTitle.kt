package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeBasicTextField
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.ProvideContentColorTextStyle
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.write_input_title_placeholder

@Composable
fun WriteInputTitle(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = Res.string.write_input_title_placeholder.value,
) {
    var isFocused by remember { mutableStateOf(false) }

    ProvideContentColorTextStyle(
        SoomjaeTheme.colorScheme.text1,
        SoomjaeTheme.typography.title2,
    ) {
        SoomjaeBasicTextField(
            state = state,
            modifier = modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            textStyle = SoomjaeTheme.typography.title2.copy(
                color = SoomjaeTheme.colorScheme.text1,
            ),
            decorator = { innerBox ->
                if (!isFocused && state.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = SoomjaeTheme.colorScheme.text4,
                    )
                }
                innerBox()
            },
        )
    }
}
