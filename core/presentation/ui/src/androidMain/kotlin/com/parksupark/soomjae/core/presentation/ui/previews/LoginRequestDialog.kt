package com.parksupark.soomjae.core.presentation.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.components.LoginRequestDialog

@Preview
@Composable
private fun LoginRequestDialogPreview() {
    AppTheme {
        SoomjaeSurface {
            LoginRequestDialog(
                onCancelClick = { },
                onConfirmClick = { },
            )
        }
    }
}
