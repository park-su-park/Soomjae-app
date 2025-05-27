package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.profile_title

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { ProfileTopBar(onAction) },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(onAction: (ProfileAction) -> Unit) {
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
