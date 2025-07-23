package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeOutlinedTextField
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.register_email_hint
import com.parksupark.soomjae.features.auth.presentation.resources.register_login_button_1
import com.parksupark.soomjae.features.auth.presentation.resources.register_login_button_2
import com.parksupark.soomjae.features.auth.presentation.resources.register_navigate_up_description
import com.parksupark.soomjae.features.auth.presentation.resources.register_password_confirm_hint
import com.parksupark.soomjae.features.auth.presentation.resources.register_password_hint
import com.parksupark.soomjae.features.auth.presentation.resources.register_register_button
import com.parksupark.soomjae.features.auth.presentation.resources.register_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    snackbarHost: @Composable () -> Unit,
) {
    SoomjaeScaffold(
        snackbarHost = snackbarHost,
        topBar = {
            RegisterTopBar(
                onBackClick = { onAction(RegisterAction.OnBackClick) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            InputSection(state = state, modifier = Modifier.fillMaxWidth())

            RegisterButton(state.canRegister, onAction)

            LoginButton(onAction)
        }
    }
}

@Composable
private fun InputSection(
    state: RegisterState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SoomjaeOutlinedTextField(
            state = state.inputEmail,
            modifier = Modifier.semantics {
                contentType = ContentType.EmailAddress
            },
            title = stringResource(Res.string.register_email_hint),
            hint = stringResource(Res.string.register_email_hint),
        )
        SoomjaeOutlinedTextField(
            state = state.inputPassword,
            modifier = Modifier.semantics {
                contentType = ContentType.Password
            },
            title = stringResource(Res.string.register_password_hint),
            hint = stringResource(Res.string.register_password_hint),
        )
        SoomjaeOutlinedTextField(
            state = state.inputConfirmPassword,
            modifier = Modifier.semantics {
                contentType = ContentType.Password
            },
            title = stringResource(Res.string.register_password_confirm_hint),
            hint = stringResource(Res.string.register_password_confirm_hint),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = {
            Text(stringResource(Res.string.register_title))
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    stringResource(Res.string.register_navigate_up_description),
                )
            }
        },
    )
}

@Composable
private fun RegisterButton(
    canRegister: Boolean,
    onAction: (RegisterAction) -> Unit,
) {
    SoomjaeButton(
        onClick = { onAction(RegisterAction.OnRegisterClick) },
        modifier = Modifier.fillMaxWidth(),
        enabled = canRegister,
    ) {
        Text(stringResource(Res.string.register_register_button))
    }
}

@Composable
private fun LoginButton(onAction: (RegisterAction) -> Unit) {
    SoomjaeTextButton(
        onClick = { onAction(RegisterAction.OnLoginClick) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(Res.string.register_login_button_1))

                append(" ")

                withStyle(style = SpanStyle(color = SoomjaeTheme.colorScheme.cta)) {
                    append(stringResource(Res.string.register_login_button_2))
                }
            },
        )
    }
}
