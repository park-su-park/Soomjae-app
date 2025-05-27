package com.parksupark.soomjae.features.profile.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileScreen
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileState

@Composable
@Preview
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            state = ProfileState(),
            onAction = {},
        )
    }
}
