package com.parksupark.soomjae.core.presentation.designsystem.preview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@Composable
@Preview
private fun SoomjaeButtonPreview() {
    AppTheme {
        SoomjaeSurface {
            SoomjaeButton(
                onClick = { },
            ) {
                Text("Button")
            }
        }
    }
}

@Composable
@Preview
private fun SoomjaeSecondaryButtonPreview() {
    AppTheme {
        SoomjaeSurface {
            SoomjaeSecondaryButton(
                onClick = { },
            ) {
                Text("Button")
            }
        }
    }
}
