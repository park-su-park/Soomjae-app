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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.domain.model.ColorTheme
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
    currentTheme: ColorTheme,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onThemeClick: (ColorTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val themes = remember {
        listOf(
            ThemeListItemData(ColorTheme.SYSTEM, Res.string.setting_theme_system, Icons.Outlined.BrightnessAuto),
            ThemeListItemData(ColorTheme.LIGHT, Res.string.setting_theme_light, Icons.Outlined.LightMode),
            ThemeListItemData(ColorTheme.DARK, Res.string.setting_theme_dark, Icons.Outlined.DarkMode),
        )
    }

    SoomjaeAlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { SoomjaeTextButton(onClick = onConfirmClick) { Text("확인") } },
        modifier = modifier,
        dismissButton = { SoomjaeTextButton(onClick = onDismissRequest) { Text("취소") } },
        text = {
            Column {
                themes.forEach { theme ->
                    ThemeListItem(
                        text = theme.text,
                        icon = theme.icon,
                        onClick = { onThemeClick(theme.theme) },
                        selected = currentTheme == theme.theme,
                    )
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
    selected: Boolean,
) {
    SoomjaeListItem(
        headlineContent = { Text(text.value) },
        modifier = Modifier.clickable(onClick = onClick),
        leadingContent = { Icon(icon, null) },
        trailingContent = {
            SoomjaeRadioButton(
                selected = selected,
                onClick = null,
            )
        },
    )
}

private data class ThemeListItemData(
    val theme: ColorTheme,
    val text: StringResource,
    val icon: ImageVector,
)
