package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.core.presentation.ui.post.model.AuthorUi

@Composable
fun PostDetailAuthorHeader(
    author: AuthorUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            author.profileImageUrl,
            size = 40.dp,
        )

        Text(text = author.nickname, maxLines = 1, style = SoomjaeTheme.typography.labelL)
    }
}
