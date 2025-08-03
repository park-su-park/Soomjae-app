package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.write_input_content_placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteInputContent(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = Res.string.write_input_content_placeholder.value,
) {
    BasicTextField(
        state = state,
        modifier = modifier.heightIn(min = 120.dp),
        textStyle = SoomjaeTheme.typography.body1,
        decorator = { innerBox ->
            if (state.text.isEmpty()) {
                Text(
                    text = hint,
                    style = SoomjaeTheme.typography.body1.copy(color = SoomjaeTheme.colorScheme.text4),
                )
            }
            innerBox()
        },
    )
}
