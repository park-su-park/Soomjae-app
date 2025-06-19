package com.parksupark.soomjae.features.setting.presentation.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.ui.resources.value
import org.jetbrains.compose.resources.StringResource

@Composable
internal fun SettingItem(
    text: StringResource,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeListItem(
        headlineContent = {
            Text(text.value)
        },
        leadingContent = {
            Icon(imageVector = icon, contentDescription = null)
        },
        modifier = modifier.clickable(onClick = onClick),
    )
}
