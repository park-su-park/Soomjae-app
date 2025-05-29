package com.parksupark.soomjae.features.auth.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.auth.presentation.register.RegisterScreen
import com.parksupark.soomjae.features.auth.presentation.register.RegisterState

@Composable
@PreviewLightDark
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {},
            snackbarHost = {},
        )
    }
}
