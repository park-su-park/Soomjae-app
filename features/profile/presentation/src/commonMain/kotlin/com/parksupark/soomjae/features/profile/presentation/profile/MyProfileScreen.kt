package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.features.profile.presentation.profile.components.ProfileTopBar
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_button
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_request
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MyProfileScreen(
    bottomBar: @Composable () -> Unit,
    state: ProfileState.MyProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { ProfileTopBar(onAction) },
        bottomBar = bottomBar,
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (state.isLogin) {
                // TODO: Display user profile information
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.weight(1f))
                    LoginRequestSection(onLoginClick = { onAction(ProfileAction.OnLoginClick) })
                    Spacer(modifier = Modifier.weight(2f))
                }
            }
        }
    }
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
