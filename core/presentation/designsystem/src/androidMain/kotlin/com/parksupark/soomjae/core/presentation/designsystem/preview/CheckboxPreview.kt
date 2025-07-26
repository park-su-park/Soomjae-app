package com.parksupark.soomjae.core.presentation.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCheckbox
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme

@Preview
@Composable
private fun SoomjaeCheckboxPreview() {
    AppTheme {
        SoomjaeCheckbox(
            checked = false,
            onCheckedChange = null,
        )
    }
}

@Preview
@Composable
private fun SoomjaeCheckboxPreview_Checked() {
    AppTheme {
        SoomjaeCheckbox(
            checked = true,
            onCheckedChange = null,
        )
    }
}

@Preview
@Composable
private fun SoomjaeCheckboxPreview_Disabled() {
    AppTheme {
        SoomjaeCheckbox(
            checked = false,
            onCheckedChange = null,
            enabled = false,
        )
    }
}

@Preview
@Composable
private fun SoomjaeCheckboxPreview_DisabledChecked() {
    AppTheme {
        SoomjaeCheckbox(
            checked = true,
            onCheckedChange = null,
            enabled = false,
        )
    }
}
