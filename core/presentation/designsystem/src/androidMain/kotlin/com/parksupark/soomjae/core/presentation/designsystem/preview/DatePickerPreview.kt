package com.parksupark.soomjae.core.presentation.designsystem.preview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeDatePicker
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DatePickerPreview() {
    AppTheme {
        SoomjaeDatePicker(
            state = rememberDatePickerState(),
        )
    }
}
