package com.parksupark.soomjae.core.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeDrawable
import com.parksupark.soomjae.core.presentation.ui.resources.value
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = SoomjaeDrawable.ic_empty_folder_screen.value,
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        EmptyContent()
    }
}
