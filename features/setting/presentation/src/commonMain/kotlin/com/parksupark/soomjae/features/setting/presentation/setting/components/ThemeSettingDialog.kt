package com.parksupark.soomjae.features.setting.presentation.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeAlertDialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeRadioButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.setting.presentation.resources.Res
import com.parksupark.soomjae.features.setting.presentation.resources.setting_theme_dark
import com.parksupark.soomjae.features.setting.presentation.resources.setting_theme_light
import com.parksupark.soomjae.features.setting.presentation.resources.setting_theme_system
import org.jetbrains.compose.resources.StringResource

@Composable
internal fun ThemeSettingDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeAlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { SoomjaeTextButton(onClick = onConfirmClick) { Text("확인") } },
        modifier = modifier,
        dismissButton = { SoomjaeTextButton(onClick = onDismissRequest) { Text("취소") } },
        text = {
            Column {
                ThemeListItem(text = Res.string.setting_theme_system, icon = Icons.Outlined.BrightnessAuto) {
                    // TODO: Implement system theme selection logic
                }
                ThemeListItem(text = Res.string.setting_theme_light, icon = Icons.Outlined.LightMode) {
                    // TODO: Implement light theme selection logic
                }
                ThemeListItem(text = Res.string.setting_theme_dark, icon = Icons.Outlined.DarkMode) {
                    // TODO: Implement dark theme selection logic
                }
            }
        },
    )
}

@Composable
private fun ThemeListItem(
    text: StringResource,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    SoomjaeListItem(
        headlineContent = { Text(text.value) },
        modifier = Modifier.clickable(onClick = onClick),
        leadingContent = { Icon(icon, null) },
        trailingContent = {
            SoomjaeRadioButton(
                selected = false,
                onClick = null,
            )
        },
    )
}
