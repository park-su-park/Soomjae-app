package com.parksupark.soomjae.features.auth.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.auth.presentation.login.LoginScreen
import com.parksupark.soomjae.features.auth.presentation.login.LoginState

@Composable
@PreviewLightDark
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {},
        )
    }
}
