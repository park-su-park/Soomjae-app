package com.parksupark.soomjae.features.post.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.write_input_title_placeholder

@Composable
internal fun WriteInputSection(
    title: TextFieldState,
    content: RichTextState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        WriteInputTitle(
            state = title,
            hint = Res.string.write_input_title_placeholder.value,
        )

        SoomjaeHorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = SoomjaeTheme.colorScheme.background2)

        WriteInputContent(
            state = content,
        )
    }
}
