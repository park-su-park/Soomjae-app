package com.parksupark.soomjae.features.post.presentation.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults.richTextEditorColors
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.write_input_content_placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WriteInputContent(state: RichTextState) {
    RichTextEditor(
        state = state,
        modifier = Modifier.heightIn(min = 120.dp),
        textStyle = SoomjaeTheme.typography.body1,
        placeholder = {
            Text(
                text = Res.string.write_input_content_placeholder.value,
                style = SoomjaeTheme.typography.body1.copy(color = SoomjaeTheme.colorScheme.text4),
            )
        },
        colors = richTextEditorColors(
            textColor = SoomjaeTheme.colorScheme.text2,
            containerColor = Color.Transparent,
            cursorColor = SoomjaeTheme.colorScheme.cta,
            errorCursorColor = SoomjaeTheme.colorScheme.error,
            selectionColors = TextSelectionColors(
                handleColor = SoomjaeTheme.colorScheme.cta,
                backgroundColor = SoomjaeTheme.colorScheme.cta.copy(alpha = 0.4f),
            ),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = SoomjaeTheme.colorScheme.text4,
        ),
    )
}
