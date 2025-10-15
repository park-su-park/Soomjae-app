package com.parksupark.soomjae.features.profile.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi

@Composable
internal fun UserProfileCard(
    user: UserUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        UserInfo()

        AdditionalInfos()

        ExtraButtons()
    }
}

@Composable
private fun UserInfo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imageRequest { data("https://picsum.photos/200") },
            contentDescription = null,
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)),
        )

        Text(
            text = "@Username",
            style = SoomjaeTheme.typography.captionL.copy(color = SoomjaeTheme.colorScheme.text2),
        )

        Text(
            text = "누구나 쉽게 배우는 스프링 부트",
            style = SoomjaeTheme.typography.captionM.copy(color = SoomjaeTheme.colorScheme.text1),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AdditionalInfos() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AdditionalInfoItem("89", "팔로잉", modifier = Modifier.weight(1f))

        AdditionalInfoItem("152k", "팔로워", modifier = Modifier.weight(1f))

        AdditionalInfoItem("25k", "좋아요", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun AdditionalInfoItem(
    numberString: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = numberString,
            style = SoomjaeTheme.typography.title2.copy(color = SoomjaeTheme.colorScheme.text1),
            textAlign = TextAlign.Center,
        )

        Text(
            text = label,
            style = SoomjaeTheme.typography.captionL.copy(color = SoomjaeTheme.colorScheme.text3),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ExtraButtons(
    onFollowClick: () -> Unit = { /* TODO: Handle follow action */ },
    onMessageClick: () -> Unit = { /* TODO: Handle message action */ },
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SoomjaeButton(onClick = onFollowClick) {
            Icon(Icons.Default.Add, null)
            Text("팔로우")
        }

        SoomjaeSecondaryButton(
            onClick = onMessageClick,
            content = { Text("메시지") },
        )
    }
}
