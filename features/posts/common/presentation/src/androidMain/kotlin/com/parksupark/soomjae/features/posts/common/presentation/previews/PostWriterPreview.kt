package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.components.PostWriteLayout

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun PostWriteLayoutPreview() {
    AppTheme {
        SoomjaeSurface {
            PostWriteLayout(
                title = { /* TODO: Title Content */ },
                body = { /* TODO: Body Content */ },
                modifier = Modifier,
                extras = { /* TODO: Extras Content */ },
            )
        }
    }
}
