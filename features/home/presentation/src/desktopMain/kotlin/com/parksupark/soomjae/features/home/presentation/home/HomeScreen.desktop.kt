package com.parksupark.soomjae.features.home.presentation.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@Composable
@Preview
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            bottomBar = {},
            state = HomeState(),
            onAction = {},
        )
    }
}
