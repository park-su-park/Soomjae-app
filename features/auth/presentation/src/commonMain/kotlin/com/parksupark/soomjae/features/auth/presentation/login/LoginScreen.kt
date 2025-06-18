package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.auth.presentation.login.components.GoogleOAuthButton
import com.parksupark.soomjae.features.auth.presentation.login.components.KakaoOAuthButton
import com.parksupark.soomjae.features.auth.presentation.login.components.NaverOAuthButton
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.login_action_close
import com.parksupark.soomjae.features.auth.presentation.resources.login_divider_text
import com.parksupark.soomjae.features.auth.presentation.resources.login_email_login_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_eula_text
import com.parksupark.soomjae.features.auth.presentation.resources.login_register_button
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { LoginScreenTopBar { onAction(LoginAction.OnCloseClick) } },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Bottom),
        ) {
            Logo(modifier = Modifier.fillMaxWidth().weight(1f))
            OAuthSection(onAction = onAction)
            LoginSectionDivider(Modifier.fillMaxWidth())
            OwnLoginSection(onAction = onAction)
            RegisterEulaText()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenTopBar(onCloseClick: () -> Unit) {
    SoomjaeTopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = onCloseClick,
                content = { Icon(Icons.Default.Close, Res.string.login_action_close.value) },
            )
        },
    )
}

@Composable
private fun Logo(modifier: Modifier = Modifier) {
    // TODO: Replace with actual logo composable
    Text(
        modifier = modifier,
        text = "Soomjae",
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.headlineLarge.copy(
            color = SoomjaeTheme.colorScheme.text1,
        ),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun OAuthSection(
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        NaverOAuthButton(
            onClick = { /*TODO*/ },
        )
        KakaoOAuthButton(
            onClick = { /*TODO*/ },
        )
        GoogleOAuthButton(
            onClick = { /*TODO*/ },
        )
    }
}

@Composable
private fun LoginSectionDivider(modifier: Modifier = Modifier) {
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
            modifier = Modifier.weight(1f).heightIn(56.dp),
        ) {
            Text(
                text = stringResource(Res.string.login_register_button),
                style = SoomjaeTheme.typography.button1,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        SoomjaeSecondaryButton(
            onClick = {
                onAction(LoginAction.OnEmailLoginClick)
            },
            modifier = Modifier.weight(1f).heightIn(56.dp),
        ) {
            Text(
                text = stringResource(Res.string.login_email_login_button),
                style = SoomjaeTheme.typography.button1,
            )
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
