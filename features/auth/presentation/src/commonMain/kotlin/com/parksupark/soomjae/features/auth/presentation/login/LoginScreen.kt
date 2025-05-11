package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.email_login_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_divider_text
import com.parksupark.soomjae.features.auth.presentation.resources.login_eula_text
import com.parksupark.soomjae.features.auth.presentation.resources.register_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    SoomjaeScaffold { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Bottom),
        ) {
            OAuthButtonsSection(onAction = onAction)
            LoginButtonsDivider(Modifier.fillMaxWidth())
            OwnLoginSection(onAction = onAction)
            RegisterEulaText()
        }
    }
}

@Composable
private fun RegisterEulaText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.login_eula_text),
        style = MaterialTheme.typography.labelSmall.copy(
            color = SoomjaeTheme.colorScheme.text3,
        ),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun OAuthButtonsSection(
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: Add OAuth buttons here
}

@Composable
private fun LoginButtonsDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Spacer(modifier = Modifier.height(1.dp).background(color = SoomjaeTheme.colorScheme.divider2).weight(1f))
        Text(
            text = stringResource(Res.string.login_divider_text),
            style = MaterialTheme.typography.labelMedium.copy(
                color = SoomjaeTheme.colorScheme.text4,
            ),
        )
        Spacer(modifier = Modifier.height(1.dp).background(color = SoomjaeTheme.colorScheme.divider2).weight(1f))
    }
}

@Composable
private fun OwnLoginSection(
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        SoomjaeButton(
            onClick = {
                onAction(LoginAction.OnRegisterClick)
            },
            modifier = Modifier.weight(1f),
        ) {
            Text(stringResource(Res.string.register_button))
        }
        Spacer(modifier = Modifier.width(8.dp))
        SoomjaeSecondaryButton(
            onClick = {
                onAction(LoginAction.OnEmailLoginClick)
            },
            modifier = Modifier.weight(1f),
        ) {
            Text(text = stringResource(Res.string.email_login_button))
        }
    }
}
