package com.parksupark.soomjae.features.auth.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginScreen
import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginState

@Composable
@Preview(name = "EmailLogin")
private fun EmailLoginScreenPreview() {
    AppTheme {
        EmailLoginScreen(
            state = EmailLoginState(),
            onAction = {},
            snackbarHost = {},
        )
    }
}
