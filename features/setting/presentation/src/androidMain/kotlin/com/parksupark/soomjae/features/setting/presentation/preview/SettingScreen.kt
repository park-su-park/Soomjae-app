package com.parksupark.soomjae.features.setting.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.setting.presentation.setting.SettingScreen
import com.parksupark.soomjae.features.setting.presentation.setting.SettingState

@Composable
@Preview(name = "Setting")
private fun SettingScreenPreview() {
    AppTheme {
        SettingScreen(
            state = SettingState(),
            onAction = {},
        )
    }
}
