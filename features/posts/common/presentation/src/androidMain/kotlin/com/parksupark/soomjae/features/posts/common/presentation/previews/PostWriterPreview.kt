package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.components.PostWriter

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun PostWriterPreview() {
    AppTheme {
        SoomjaeSurface {
            PostWriter(
                title = { /* TODO: Title Content */ },
                body = { /* TODO: Body Content */ },
                modifier = Modifier,
                extras = { /* TODO: Extras Content */ },
            )
        }
    }
}
