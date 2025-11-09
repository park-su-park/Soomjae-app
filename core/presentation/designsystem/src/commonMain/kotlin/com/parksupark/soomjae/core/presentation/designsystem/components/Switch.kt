package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.dp
import com.composeunstyled.Thumb
import com.composeunstyled.ToggleSwitch
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SoomjaeToggleSwitch(
    toggled: Boolean,
    modifier: Modifier = Modifier,
    onChangeToggle: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
) {
    val colorScheme = SoomjaeTheme.colorScheme
    val animateTrackColor by animateColorAsState(
        targetValue = if (toggled) {
            colorScheme.primary
        } else {
            colorScheme.background3
        },
    )

    ToggleSwitch(
        toggled = toggled,
        modifier = modifier.width(50.dp).drawBehind(
            onDraw = {
                drawRoundRect(
                    color = animateTrackColor,
                    size = this.size.copy(
                        width = this.size.width,
                        height = this.size.height,
                    ),
                    cornerRadius = CornerRadius(
                        x = this.size.height / 2,
                        y = this.size.height / 2,
                    ),
                )
            },
        ),
        onToggled = onChangeToggle,
        enabled = enabled,
        shape = RoundedCornerShape(100),
        contentPadding = PaddingValues(2.dp),
        thumb = {
            Thumb(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = colorScheme.divider1,
                    shape = CircleShape,
                ),
                shape = CircleShape,
                color = colorScheme.text1W,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun SoomjaeSwitchButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp).safeDrawingPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                var toggled1 by remember { mutableStateOf(true) }
                SoomjaeToggleSwitch(
                    toggled = toggled1,
                    onChangeToggle = { toggled1 = it },
                )

                var toggled2 by remember { mutableStateOf(false) }
                SoomjaeToggleSwitch(
                    toggled = toggled2,
                    onChangeToggle = { toggled2 = it },
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                var toggled1 by remember { mutableStateOf(true) }
                SoomjaeToggleSwitch(
                    toggled = toggled1,
                    onChangeToggle = { toggled1 = it },
                    enabled = false,
                )

                var toggled2 by remember { mutableStateOf(false) }
                SoomjaeToggleSwitch(
                    toggled = toggled2,
                    onChangeToggle = { toggled2 = it },
                    enabled = false,
                )
            }
        }
    }
}
