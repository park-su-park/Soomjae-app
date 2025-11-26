package com.parksupark.soomjae.features.profile.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.profile.presentation.profile.MyProfileScreen
import com.parksupark.soomjae.features.profile.presentation.profile.OthersProfileScreen
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileState

@Composable
@Preview("Loading Profile Screen")
private fun MyProfileScreen_LoadingPreview() {
    AppTheme {
        MyProfileScreen(
            bottomBar = {},
            state = ProfileState.My(
                isLoading = true,
            ),
            onAction = {},
        )
    }
}

@Composable
@Preview("My Profile Screen")
private fun MyProfileScreenPreview_User() {
    AppTheme {
        MyProfileScreen(
            bottomBar = {},
            state = ProfileState.My(
                isLoading = false,
                isLoggedIn = true,
            ),
            onAction = {},
        )
    }
}

@Composable
@Preview("Guest Profile Screen")
private fun MyProfileScreenPreview_Guest() {
    AppTheme {
        MyProfileScreen(
            bottomBar = {},
            state = ProfileState.My(
                isLoading = false,
            ),
            onAction = {},
        )
    }
}

@Composable
@Preview("Others Profile Screen")
private fun OthersProfileScreenPreview() {
    AppTheme {
        OthersProfileScreen(
            state = ProfileState.Other(),
            onAction = {},
        )
    }
}
