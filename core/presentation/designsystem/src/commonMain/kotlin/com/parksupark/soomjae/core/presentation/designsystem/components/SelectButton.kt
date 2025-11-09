package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeColors
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

data class SoomjaeSelectButtonColors(
    val unSelectedTextColor: Color,
    val selectedTextColor: Color,
)

@Composable
fun SoomjaeSelectButton(
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    colors: SoomjaeSelectButtonColors = SoomjaeSelectButtonDefaults.colors(),
    endIcon: @Composable (() -> Unit)? = {
        SoomjaeIcon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
        )
    },
    enabled: Boolean = true,
) {
    val decoratedText: @Composable () -> Unit = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.button1.copy(
                color = if (selected) {
                    colors.selectedTextColor
                } else {
                    colors.unSelectedTextColor
                },
            ),
            content = { text() },
        )
    }

    Row(
        modifier = modifier.clip(RoundedCornerShape(8.dp))
            .then(
                if (enabled && onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            )
            .border(
                width = 1.dp,
                brush = SolidColor(SoomjaeTheme.colorScheme.divider1),
                shape = RoundedCornerShape(8.dp),
            ).padding(12.dp)
            .semantics { role = Role.DropdownList },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        decoratedText()

        endIcon?.invoke()
    }
}

object SoomjaeSelectButtonDefaults {
    private val SoomjaeColors.defaultSelectButtonColors: SoomjaeSelectButtonColors
        @Composable get() = this.defaultSelectButtonColorsCached ?: SoomjaeSelectButtonColors(
            unSelectedTextColor = SoomjaeTheme.colorScheme.text4,
            selectedTextColor = SoomjaeTheme.colorScheme.text1,
        ).also { defaultSelectButtonColorsCached = it }

    @Composable
    fun colors(): SoomjaeSelectButtonColors = SoomjaeTheme.colorScheme.defaultSelectButtonColors

    @Composable
    fun colors(
        unSelectedTextColor: Color,
        selectedTextColor: Color,
    ): SoomjaeSelectButtonColors = SoomjaeSelectButtonColors(
        unSelectedTextColor = unSelectedTextColor,
        selectedTextColor = selectedTextColor,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SoomjaeSelectButton(
                text = { Text("카테고리를 선택 해주세요.") },
                selected = true,
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )
            SoomjaeSelectButton(
                text = { Text("카테고리를 선택 해주세요.") },
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )
        }
    }
}
