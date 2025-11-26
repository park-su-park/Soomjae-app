package com.parksupark.soomjae.features.auth.presentation.login.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.LocalThemeIsDark
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.google
import com.parksupark.soomjae.features.auth.presentation.resources.kakao
import com.parksupark.soomjae.features.auth.presentation.resources.login_google_oauth_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_kakao_oauth_button
import com.parksupark.soomjae.features.auth.presentation.resources.login_naver_oauth_button
import com.parksupark.soomjae.features.auth.presentation.resources.naver
import org.jetbrains.compose.resources.StringResource

@Composable
internal fun NaverOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OAuthButton(
        onClick = onClick,
        text = Res.string.login_naver_oauth_button,
        colors = ButtonColors(
            containerColor = Color(0xFF03C75A),
            contentColor = SoomjaeTheme.colorScheme.text1W,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier,
        icon = {
            Icon(painter = Res.drawable.naver.value, contentDescription = null)
        },
    )
}

@Composable
internal fun KakaoOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OAuthButton(
        onClick = onClick,
        text = Res.string.login_kakao_oauth_button,
        colors = ButtonColors(
            containerColor = Color(0xFFFEE500),
            contentColor = Color.Black.copy(alpha = 0.85f),
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier,
        icon = {
            Icon(painter = Res.drawable.kakao.value, contentDescription = null)
        },
    )
}

@Composable
internal fun GoogleOAuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark by LocalThemeIsDark.current

    val textColor = if (isDark) Color(0xFFE3E3E3) else Color(0xFF1F1F1F)
    val containerColor = if (isDark) Color(0xFF131314) else Color.White
    val strokeColor = if (isDark) Color(0xFF8E918F) else Color(0xFF747775)

    OAuthButton(
        onClick = onClick,
        text = Res.string.login_google_oauth_button,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = textColor,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        modifier = modifier.border(1.dp, strokeColor, RoundedCornerShape(12.dp)),
        icon = {
            Icon(
                painter = Res.drawable.google.value,
                contentDescription = null,
                tint = Color.Unspecified,
            )
        },
    )
}

@Composable
private fun OAuthButton(
    onClick: () -> Unit,
    text: StringResource,
    colors: ButtonColors,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
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
            icon()

            Text(
                text = text.value,
                modifier = Modifier.align(alignment = Alignment.Center),
                style = SoomjaeTheme.typography.button1.copy(
                    color = colors.contentColor,
                ),
            )
        }
    }
}
