package com.parksupark.soomjae.features.posts.community.presentation.tab.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi

@Composable
internal fun CommunityPostCard(
    post: CommunityPostUi,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PostAuthorHeader(
            author = post.author,
            subtitle = post.formattedCreatedAt,
        )

        PostCardTitle(title = post.title)

        PostCardContent(post.content)

        AdditionalActions(onFavoriteClick = onFavoriteClick)
    }
}

@Composable
private fun PostAuthorHeader(
    author: AuthorUi,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageRequest { data(author.profileImageUrl) },
            contentDescription = null,
            modifier = Modifier.size(36.dp),
        )

        Column {
            Text(text = author.nickname, style = SoomjaeTheme.typography.labelL, maxLines = 1)
            Text(
                text = subtitle,
                style = SoomjaeTheme.typography.labelS.copy(
                    color = SoomjaeTheme.colorScheme.text4,
                ),
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun PostCardTitle(title: String) {
    Text(text = title, style = SoomjaeTheme.typography.title3, maxLines = 1)
}

@Composable
private fun PostCardContent(
    contentPreview: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = contentPreview,
        style = SoomjaeTheme.typography.captionL,
        maxLines = 2,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun AdditionalActions(
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        IconButton(
            onClick = onFavoriteClick,
            content = {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = SoomjaeTheme.colorScheme.icon,
                )
            },
        )
    }
}
