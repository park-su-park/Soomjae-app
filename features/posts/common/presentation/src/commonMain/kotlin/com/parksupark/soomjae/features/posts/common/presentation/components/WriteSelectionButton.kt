package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSelectButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WriteSelectionButton(
    label: String,
    onClick: () -> Unit,
    buttonText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    enabled: Boolean = true,
) {
    WriteSelectionLayout(
        label = label,
        modifier = modifier,
    ) {
        SoomjaeSelectButton(
            text = buttonText,
            modifier = Modifier.fillMaxWidth(),
            selected = selected,
            onClick = onClick,
            enabled = enabled,
        )
    }
}

@Composable
fun WriteSelectionLayout(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth().padding(16.dp),
    ) {
        Text(label, style = SoomjaeTheme.typography.labelL)

        content()
    }
}

@Composable
fun WriteSelectionLayout(
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val decoratedHeader: @Composable ColumnScope.() -> Unit = {
        ProvideTextStyle(value = SoomjaeTheme.typography.labelL) {
            header()
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        decoratedHeader()

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteSelectionButtonPreview() {
    AppTheme {
        WriteSelectionButton(
            label = "카테고리",
            onClick = { },
            buttonText = { Text("카테고리 선택") },
        )
    }
}
