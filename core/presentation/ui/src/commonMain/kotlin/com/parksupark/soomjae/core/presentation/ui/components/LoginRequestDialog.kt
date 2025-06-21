package com.parksupark.soomjae.core.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeAlertDialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.login_request_dialog_cancel_button
import com.parksupark.soomjae.core.presentation.ui.resources.login_request_dialog_confirm_button
import com.parksupark.soomjae.core.presentation.ui.resources.login_request_dialog_text
import com.parksupark.soomjae.core.presentation.ui.resources.login_request_dialog_title1
import com.parksupark.soomjae.core.presentation.ui.resources.login_request_dialog_title2
import com.parksupark.soomjae.core.presentation.ui.resources.value

@Composable
fun LoginRequestDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeAlertDialog(
        onDismissRequest = { },
        confirmButton = {
            SoomjaeTextButton(
                onClick = onConfirmClick,
                content = { Text(Res.string.login_request_dialog_confirm_button.value) },
            )
        },
        modifier = modifier,
        dismissButton = {
            SoomjaeTextButton(
                onClick = onCancelClick,
                content = { Text(Res.string.login_request_dialog_cancel_button.value) },
            )
        },
        title = {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(Res.string.login_request_dialog_title1.value)
                    }
                    append(Res.string.login_request_dialog_title2.value)
                },
            )
        },
        text = {
            Text(Res.string.login_request_dialog_text.value)
        },
    )
}
