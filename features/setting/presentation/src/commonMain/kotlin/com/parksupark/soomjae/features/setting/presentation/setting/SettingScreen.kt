package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.parksupark.soomjae.core.domain.model.ColorTheme
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.setting.presentation.resources.Res
import com.parksupark.soomjae.features.setting.presentation.resources.setting_item_theme
import com.parksupark.soomjae.features.setting.presentation.resources.setting_navigate_up_description
import com.parksupark.soomjae.features.setting.presentation.resources.setting_title
import com.parksupark.soomjae.features.setting.presentation.setting.components.SettingItem
import com.parksupark.soomjae.features.setting.presentation.setting.components.ThemeSettingDialog

@Composable
internal fun SettingScreen(
    state: SettingState,
    onAction: (SettingAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { SettingTopBar(onBackClick = { onAction(SettingAction.OnBackClick) }) },
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            themeSetting(
                currentTheme = state.colorTheme,
                onThemeChange = { onAction(SettingAction.OnThemeChange(it)) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingTopBar(onBackClick: () -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.setting_title.value) },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.setting_navigate_up_description.value,
                    )
                },
            )
        },
    )
}

private fun LazyListScope.themeSetting(
    currentTheme: ColorTheme,
    onThemeChange: (ColorTheme) -> Unit,
) = item {
    var dialogOpen by remember { mutableStateOf(false) }

    SettingItem(
        text = Res.string.setting_item_theme,
        icon = Icons.Default.Contrast,
        onClick = { dialogOpen = true },
    )

    if (dialogOpen) {
        ThemeSettingDialog(
            onDismissRequest = { dialogOpen = false },
            onConfirmClick = { dialogOpen = false },
            onThemeClick = onThemeChange,
            currentTheme = currentTheme,
        )
    }
}
