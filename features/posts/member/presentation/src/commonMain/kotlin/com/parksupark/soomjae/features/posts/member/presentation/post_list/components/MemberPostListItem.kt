package com.parksupark.soomjae.features.posts.member.presentation.post_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.coil3.Coil3ImageLoader
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.MemberPostUi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MemberPostListItem(
    post: MemberPostUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = imageRequest { data(post.author.profileImageUrl) },
            contentDescription = null,
            modifier = Modifier.size(48.dp)
                .clip(RoundedCornerShape(12.dp)),
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            MemberPostListItemHeather(
                nickname = post.author.nickname,
                createdAt = post.formattedCreatedAt,
                content = post.content,
            )

            MemberPostListItemActions(
                isLiked = post.isLiked,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun MemberPostListItemHeather(
    nickname: String,
    createdAt: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    val richTextState = rememberRichTextState()

    LaunchedEffect(content) {
        richTextState.setHtml(content)
    }

    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = nickname, style = SoomjaeTheme.typography.labelM)
                Text(
                    text = createdAt,
                    color = SoomjaeTheme.colorScheme.text3,
                    style = SoomjaeTheme.typography.captionM,
                )
            }

            RichText(
                state = richTextState,
                imageLoader = Coil3ImageLoader,
                style = SoomjaeTheme.typography.captionL,
                modifier = Modifier.background(color = Color.Magenta),
            )
        }

        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = null,
            tint = SoomjaeTheme.colorScheme.icon,
        )
    }
}

@Composable
private fun MemberPostListItemActions(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        ActionItem(
            imageVector = Icons.AutoMirrored.Outlined.Comment,
            contentDescription = "Comment",
            count = 12,
        )
        ActionItem(
            imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Like",
            count = 34,
        )
    }
}

@Composable
private fun ActionItem(
    imageVector: ImageVector,
    contentDescription: String,
    count: Long,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = SoomjaeTheme.colorScheme.icon,
        )

        Text(
            text = count.toString(),
            color = SoomjaeTheme.colorScheme.text2,
            style = SoomjaeTheme.typography.captionM,
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun MemberPostListItemPreview() {
    AppTheme {
        SoomjaeSurface {
            MemberPostListItem(
                post = MemberPostUi(
                    id = 1,
                    author = AuthorUi(
                        id = "1",
                        nickname = "nickname",
                        profileImageUrl = "https://picsum.photos/200",
                    ),
                    createdAt = Clock.System.now(),
                    isLiked = true,
                    content = "<p>안녕하세요!<br/>반갑습니다 :)</p><p><strong>오늘도 좋은 하루 보내세요!</strong></p><p><br/>" +
                        "</p><p><img src=\"https://picsum.photos/200\" alt=\"Sample Image\"/></p>",
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
