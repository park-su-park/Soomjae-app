package com.parksupark.soomjae.core.presentation.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@Preview
@Composable
private fun SoomjaeCircularProgressIndicatorPreview() {
    AppTheme {
        SoomjaeSurface {
            SoomjaeCircularProgressIndicator()
        }
    }
}
