package com.parksupark.soomjae.features.profile.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi
import com.valentinilk.shimmer.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun UserProfileCard(
    user: UserUi,
    modifier: Modifier = Modifier,
    isMyProfile: Boolean = false,
    onEditProfileClick: () -> Unit = { },
) {
    Column(
        modifier = modifier.background(SoomjaeTheme.colorScheme.background1)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        UserInfo(user)

        if (isMyProfile) {
            EditProfileButton(onClick = onEditProfileClick)
        } else {
            ExtraButtons()
        }
    }
}

@Composable
private fun UserInfo(
    user: UserUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImage(imageUrl = user.profileImageUrl)
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = user.nickname,
            color = SoomjaeTheme.colorScheme.text2,
            style = SoomjaeTheme.typography.title3,
        )

        Text(
            text = user.bio,
            color = SoomjaeTheme.colorScheme.text1,
            style = SoomjaeTheme.typography.captionS,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun EditProfileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeSecondaryButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        content = {
            SoomjaeIcon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("프로필 수정")
        },
    )
}

@Composable
private fun ExtraButtons(
    onFollowClick: () -> Unit = { },
    onMessageClick: () -> Unit = { },
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SoomjaeButton(onClick = onFollowClick) {
            Icon(Icons.Default.Add, null)
            Spacer(modifier = Modifier.width(4.dp))
            Text("팔로우")
        }

        SoomjaeSecondaryButton(
            onClick = onMessageClick,
            content = {
                Icon(Icons.AutoMirrored.Outlined.Message, null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("메시지")
            },
        )
    }
}

@Composable
internal fun UserProfileCardSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SoomjaeTheme.colorScheme.background1,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp)
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Profile image placeholder
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(24))
                .background(SoomjaeTheme.colorScheme.background3),
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Username placeholder
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(14.dp)
                .background(SoomjaeTheme.colorScheme.background3),
        )

        // Bio placeholder (multi-line, centered)
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(12.dp)
                .background(SoomjaeTheme.colorScheme.background3),
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Buttons placeholder row
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SoomjaeTheme.colorScheme.background3),
            )
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SoomjaeTheme.colorScheme.background3),
            )
        }
    }
}

@Preview
@Composable
private fun UserProfileCard_MyProfilePreview() {
    AppTheme {
        UserProfileCard(
            user = UserUi(
                id = 1,
                nickname = "Username",
                bio = "This is a sample bio for the user profile.",
            ),
            isMyProfile = true,
        )
    }
}

@Preview
@Composable
private fun UserProfileCard_OtherProfilePreview() {
    AppTheme {
        UserProfileCard(
            user = UserUi(
                id = 1,
                nickname = "Username",
                bio = "This is a sample bio for the user profile.",
            ),
        )
    }
}

@Preview
@Composable
private fun UserProfileCard_SkeletonPreview() {
    AppTheme {
        UserProfileCardSkeleton(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
