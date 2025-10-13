package com.parksupark.soomjae.features.posts.community.presentation.tab.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.minimumInteractiveComponentSize
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
import com.parksupark.soomjae.features.posts.common.presentation.components.PostActionItem
import com.parksupark.soomjae.features.posts.common.presentation.components.PostCard
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionType
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionUi
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi

@Composable
internal fun CommunityPostCard(
    post: CommunityPostUi,
    canLike: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PostCard(
        title = { PostCardTitle(title = post.title) },
        modifier = modifier,
        header = {
            PostAuthorHeader(
                author = post.author,
                subtitle = post.formattedCreatedAt,
            )
        },
        body = {
            PostCardContent(contentPreview = post.content)
        },
        footer = {
            AdditionalActions(
                isUserLiked = post.isUserLiked,
                likeCount = post.likeCount.toLong(),
                canLike = canLike,
                commentCount = post.commentCount.toLong(),
                onFavoriteClick = onFavoriteClick,
            )
        },
    )
}

@Composable
private fun PostAuthorHeader(
    author: AuthorUi,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = imageRequest { data(author.profileImageUrl) },
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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

        IconButton(
            onClick = { },
            content = {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "More options",
                )
            },
        )
    }
}

@Composable
private fun PostCardTitle(title: String) {
    Text(text = title, maxLines = 1)
}

@Composable
private fun PostCardContent(
    contentPreview: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = contentPreview,
        maxLines = 2,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun AdditionalActions(
    isUserLiked: Boolean,
    likeCount: Long,
    canLike: Boolean,
    commentCount: Long,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PostActionItem(
            action = PostActionUi(
                type = PostActionType.Like,
                isSelected = isUserLiked,
                count = likeCount,
                isEnabled = canLike,
                onClick = onFavoriteClick,
            ),
            modifier = Modifier.minimumInteractiveComponentSize(),
        )

        PostActionItem(
            action = PostActionUi(
                type = PostActionType.Comment,
                count = commentCount,
            ),
            modifier = Modifier.minimumInteractiveComponentSize(),
        )
    }
}
