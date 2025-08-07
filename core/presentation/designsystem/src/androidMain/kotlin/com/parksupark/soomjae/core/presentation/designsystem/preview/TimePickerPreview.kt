package com.parksupark.soomjae.core.presentation.designsystem.preview

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTimePicker
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun SoomjaeTimePickerPreview() {
    AppTheme {
        SoomjaeSurface(color = SoomjaeTheme.colorScheme.background2) {
            SoomjaeTimePicker(
                state = rememberTimePickerState(),
                modifier = Modifier.safeContentPadding(),
            )
        }
    }
}
