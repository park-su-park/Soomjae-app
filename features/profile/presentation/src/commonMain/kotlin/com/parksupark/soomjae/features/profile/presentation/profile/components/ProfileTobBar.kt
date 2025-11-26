package com.parksupark.soomjae.features.profile.presentation.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileAction
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.profile_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileTopBar(onAction: (ProfileAction) -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.profile_title.value) },
        actions = {
            IconButton(
                { onAction(ProfileAction.OnSettingClick) },
                content = { Icon(Icons.Outlined.Settings, "") },
            )
        },
    )
}
