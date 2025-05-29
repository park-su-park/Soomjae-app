package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextField
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.email_login_button_login
import com.parksupark.soomjae.features.auth.presentation.resources.email_login_textfield_email_hint
import com.parksupark.soomjae.features.auth.presentation.resources.email_login_textfield_password_hint
import com.parksupark.soomjae.features.auth.presentation.resources.email_login_title

@Composable
internal fun EmailLoginScreen(
    state: EmailLoginState,
    onAction: (EmailLoginAction) -> Unit,
    snackbarHost: @Composable (() -> Unit),
) {
    SoomjaeScaffold(
        topBar = { EmailLoginTopBar(onBackClick = { onAction(EmailLoginAction.OnBackClick) }) },
        snackbarHost = snackbarHost,
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputSection(state)

            LoginButton(onAction, isEnabled = { state.canLogin })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailLoginTopBar(onBackClick: () -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.email_login_title.value) },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = { Icon(Icons.AutoMirrored.Default.ArrowBack, "") },
            )
        },
    )
}

@Composable
private fun InputSection(state: EmailLoginState) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SoomjaeTextField(state = state.inputEmail, hint = Res.string.email_login_textfield_email_hint.value)

        SoomjaeTextField(state = state.inputPassword, hint = Res.string.email_login_textfield_password_hint.value)
    }
}

@Composable
private fun LoginButton(
    onAction: (EmailLoginAction) -> Unit,
    isEnabled: () -> Boolean,
) {
    SoomjaeButton(
        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
        onClick = { onAction(EmailLoginAction.OnLoginClick) },
        content = { Text(Res.string.email_login_button_login.value) },
        enabled = isEnabled(),
    )
}
