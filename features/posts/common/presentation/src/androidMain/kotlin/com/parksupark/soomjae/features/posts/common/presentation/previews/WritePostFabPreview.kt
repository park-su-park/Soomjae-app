package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab

@Preview
@Composable
private fun WritePostFabPreview() {
    AppTheme {
        SoomjaeSurface {
            WritePostFab(
                onClick = { },
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}
