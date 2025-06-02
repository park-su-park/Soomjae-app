package com.parksupark.soomjae.features.auth.presentation.login.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.login_google_oauth_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_kakao_oauth_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_naver_oauth_button

@Composable
internal fun NaverOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OAuthButton(
        onClick = onClick,
        icon = Icons.Outlined.Error,
        text = Res.string.login_naver_oauth_button.value,
        colors = ButtonColors(
            containerColor = Color(0xFF00D03F),
            contentColor = SoomjaeTheme.colorScheme.text1,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier,
    )
}

@Composable
internal fun KakaoOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OAuthButton(
        onClick = onClick,
        icon = Icons.Outlined.Error,
        text = Res.string.login_kakao_oauth_button.value,
        textColor = Color(0xFF212121),
        colors = ButtonColors(
            containerColor = Color(0xFFFAE100),
            contentColor = SoomjaeTheme.colorScheme.text1,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier,
    )
}

@Composable
internal fun GoogleOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OAuthButton(
        onClick = onClick,
        icon = Icons.Outlined.Error,
        text = Res.string.login_google_oauth_button.value,
        textColor = SoomjaeTheme.colorScheme.text1,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = SoomjaeTheme.colorScheme.text1,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier.border(1.dp, SoomjaeTheme.colorScheme.divider1, RoundedCornerShape(12.dp)),
    )
}

@Composable
private fun OAuthButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    colors: ButtonColors,
    modifier: Modifier = Modifier,
    textColor: Color? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = 56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = colors,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(alignment = Alignment.CenterStart),
            )

            Text(
                text = text,
                modifier = Modifier.align(alignment = Alignment.Center),
                style = SoomjaeTheme.typography.button1.copy(
                    color = textColor ?: SoomjaeTheme.colorScheme.text1W,
                ),
            )
        }
    }
}
