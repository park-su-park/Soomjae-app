package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.profile.presentation.profile.components.UserProfileCard
import com.parksupark.soomjae.features.profile.presentation.profile.mdoels.UserUi
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_button
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_request
import com.parksupark.soomjae.features.profile.presentation.resources.profile_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MyProfileScreen(
    bottomBar: @Composable () -> Unit,
    state: ProfileState.MyProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { MyProfileTopBar { onAction(ProfileAction.OnSettingClick) } },
        bottomBar = bottomBar,
    ) { innerPadding ->

        if (state.isLoggedIn) {
            MyProfileContent(
                modifier = Modifier.padding(innerPadding),
                user = state.user,
            )
        } else {
            GuestProfileContent(
                modifier = Modifier.padding(innerPadding),
                onLoginClick = { onAction(ProfileAction.OnLoginClick) },
            )
        }
    }
}

@Composable
private fun MyProfileContent(
    user: UserUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        UserProfileCard(
            user = user,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun GuestProfileContent(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LoginRequestSection(onLoginClick = onLoginClick)
        Spacer(modifier = Modifier.weight(2f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyProfileTopBar(onSettingClick: () -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.profile_title.value) },
        actions = {
            IconButton(
                onClick = onSettingClick,
                content = { Icon(Icons.Outlined.Settings, "") },
            )
        },
    )
}

@Composable
private fun LoginRequestSection(onLoginClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.my_profile_login_request),
            style = SoomjaeTheme.typography.labelL.copy(color = SoomjaeTheme.colorScheme.text2),
        )

        SoomjaeButton(
            onClick = onLoginClick,
            content = {
                Icon(Icons.Default.Add, contentDescription = null)

                Text(stringResource(Res.string.my_profile_login_button))
            },
        )
    }
}
