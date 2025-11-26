package com.parksupark.soomjae.core.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeDrawable
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = SoomjaeDrawable.ic_empty_folder_screen.value,
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "텅",
            color = SoomjaeTheme.colorScheme.text3,
            style = SoomjaeTheme.typography.headlineS,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        EmptyContent()
    }
}
