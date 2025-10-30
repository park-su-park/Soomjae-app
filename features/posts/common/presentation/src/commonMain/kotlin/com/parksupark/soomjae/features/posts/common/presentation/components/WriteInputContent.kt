package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeBasicTextField
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.ProvideContentColorTextStyle
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.write_input_content_placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteInputContent(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = Res.string.write_input_content_placeholder.value,
) {
    var isFocused by remember { mutableStateOf(false) }

    ProvideContentColorTextStyle(
        SoomjaeTheme.colorScheme.text2,
        SoomjaeTheme.typography.body1,
    ) {
        SoomjaeBasicTextField(
            state = state,
            modifier = modifier.onFocusChanged {
                isFocused = it.isFocused
            }.heightIn(min = 120.dp),
            textStyle = SoomjaeTheme.typography.body1.copy(
                color = SoomjaeTheme.colorScheme.text2,
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
