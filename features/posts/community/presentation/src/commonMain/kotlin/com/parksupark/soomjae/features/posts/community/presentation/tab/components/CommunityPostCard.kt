package com.parksupark.soomjae.features.posts.community.presentation.tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeVerticalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.comment
import com.parksupark.soomjae.core.presentation.designsystem.theme.like
import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.utils.toDp
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi

@Composable
internal fun CommunityPostCard(
    post: CommunityPostUi,
    modifier: Modifier = Modifier,
) {
    SoomjaeSurface(modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PostCardHeader(category = post.category)

            PostCardTitle(title = post.title)

            PostCardContent(content = post.content)

            PostCardAdditionalInfos(
                likeCount = post.likeCount,
                isUserLiked = post.isUserLiked,
                commentCount = post.commentCount,
                createdAt = post.formattedCreatedAt,
                author = post.author.nickname,
            )
        }
    }
}

@Composable
private fun PostCardHeader(category: CategoryUi?) {
    category?.let {
        Text(
            text = it.name,
            style = SoomjaeTheme.typography.captionS,
            color = SoomjaeTheme.colorScheme.text3,
            modifier = Modifier.background(
                color = SoomjaeTheme.colorScheme.background3,
                shape = MaterialTheme.shapes.extraSmall,
            ).padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
        )
    }
}

@Composable
private fun PostCardTitle(title: String) {
    Text(text = title, style = SoomjaeTheme.typography.title3, maxLines = 1)
}

@Composable
private fun PostCardContent(
    content: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = content,
        style = SoomjaeTheme.typography.captionL,
        maxLines = 2,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun PostCardAdditionalInfos(
    likeCount: Int,
    isUserLiked: Boolean,
    commentCount: Int,
    createdAt: String,
    author: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val height = SoomjaeTheme.typography.captionS.fontSize.toDp()

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (isUserLiked) {
                    Icons.Outlined.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                },
                contentDescription = null,
                modifier = Modifier.size(height),
                tint = SoomjaeTheme.colorScheme.like,
            )
            Text(
                text = likeCount.toString(),
                style = SoomjaeTheme.typography.captionS,
                color = SoomjaeTheme.colorScheme.like,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Comment,
                contentDescription = null,
                modifier = Modifier.size(height),
                tint = SoomjaeTheme.colorScheme.comment,
            )
            Text(
                text = commentCount.toString(),
                style = SoomjaeTheme.typography.captionS,
                color = SoomjaeTheme.colorScheme.comment,
            )
        }

        SoomjaeVerticalDivider(
            modifier = Modifier.height(height),
            color = SoomjaeTheme.colorScheme.divider2,
        )

        Text(
            text = createdAt,
            style = SoomjaeTheme.typography.captionS,
            color = SoomjaeTheme.colorScheme.text3,
        )

        SoomjaeVerticalDivider(
            modifier = Modifier.height(height),
            color = SoomjaeTheme.colorScheme.divider2,
        )

        Text(
            text = author,
            style = SoomjaeTheme.typography.captionS,
            color = SoomjaeTheme.colorScheme.text3,
        )
    }
}
