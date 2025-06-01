package com.parksupark.soomjae.features.profile.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.profile.presentation.profile.components.UserProfileCard
import com.parksupark.soomjae.features.profile.presentation.profile.mdoels.UserUi

@Preview
@Composable
private fun UserProfileCardPreview() {
    AppTheme {
        SoomjaeSurface {
            UserProfileCard(
                user = UserUi.Default,
            )
        }
    }
}
