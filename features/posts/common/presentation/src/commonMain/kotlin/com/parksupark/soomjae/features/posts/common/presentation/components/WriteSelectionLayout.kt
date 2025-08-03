package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun WriteSelectionLayout(
    label: String,
    onClick: () -> Unit,
    buttonText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SoomjaeHorizontalDivider()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(label, style = SoomjaeTheme.typography.labelL)

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = Color.Transparent,
                    contentColor = SoomjaeTheme.colorScheme.text2,
                ),
                border = BorderStroke(1.dp, SoomjaeTheme.colorScheme.divider1),
            ) {
                buttonText()
            }
        }
    }
}
