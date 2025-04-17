package com.parksupark.soomjae.features.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@Composable
@Preview
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {},
        )
    }
}
