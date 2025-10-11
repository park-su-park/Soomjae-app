package com.parksupark.soomjae.features.posts.member.presentation.post_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.parksupark.soomjae.core.presentation.designsystem.components.ExpandableText
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.core.presentation.ui.utils.pxToDp
import com.parksupark.soomjae.features.posts.common.presentation.components.PostActionItem
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionType
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionUi
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.MemberPostUi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MemberPostListItem(
    post: MemberPostUi,
    onMenuClick: () -> Unit,
    onCommentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MemberPostListItemLayout(
        headlineContent = {
            MemberPostListItemHeader(
                author = post.author,
                onMenuClick = onMenuClick,
                modifier = Modifier.fillMaxWidth().padding(12.dp),
            )
        },
        bodyContent = {
            MemberPostListItemBody(
                images = post.images,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        footerContent = {
            MemberPostListItemFooter(
                isLiked = post.isLiked,
                likeCount = post.likeCount,
                commentCount = post.commentCount,
                onCommentClick = onCommentClick,
                content = post.content,
                createdAt = post.formattedCreatedAt,
                modifier = Modifier.fillMaxWidth().padding(12.dp),
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun MemberPostListItemLayout(
    headlineContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit,
    footerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val decoratedHeadlineContent: @Composable () -> Unit = {
        ProvideTextStyle(
            SoomjaeTheme.typography.labelL,
        ) {
            headlineContent()
        }
    }

    val decoratedBodyContent: @Composable () -> Unit = {
        ProvideTextStyle(
            SoomjaeTheme.typography.body1,
        ) {
            bodyContent()
        }
    }

    val decoratedFooterContent: @Composable () -> Unit = {
        ProvideTextStyle(
            SoomjaeTheme.typography.labelL,
        ) {
            footerContent()
        }
    }

    SoomjaeSurface(modifier = modifier) {
        Column {
            decoratedHeadlineContent()
            decoratedBodyContent()
            decoratedFooterContent()
        }
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun MemberPostListItemHeader(
    author: AuthorUi,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = imageRequest { data(author.profileImageUrl) },
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(20.dp)),
            )

            Text(text = author.nickname)
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Menu",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp).clickable { onMenuClick() },
        )
    }
}

@Composable
private fun MemberPostListItemBody(
    images: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.pxToDp()
    val state = rememberPagerState {
        images.count()
    }

    HorizontalPager(
        state = state,
        modifier = modifier.heightIn(min = screenWidth),
    ) { page ->
        AsyncImage(
            model = imageRequest { data(images[page]) },
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun MemberPostListItemFooter(
    isLiked: Boolean,
    likeCount: Long,
    commentCount: Long,
    onCommentClick: () -> Unit,
    content: String,
    createdAt: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MemberPostListItemActions(
            isLiked = isLiked,
            likeCount = likeCount,
            commentCount = commentCount,
            onCommentClick = onCommentClick,
            modifier = Modifier.fillMaxWidth(),
        )

        MemberPostListContent(
            content = content,
            createdAt = createdAt,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun MemberPostListItemActions(
    isLiked: Boolean,
    likeCount: Long,
    commentCount: Long,
    onCommentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PostActionItem(
            action = PostActionUi(
                type = PostActionType.Like,
                count = likeCount,
                isSelected = isLiked,
                onClick = { /* TODO */ },
            ),
        )
        PostActionItem(
            action = PostActionUi(
                type = PostActionType.Comment,
                count = commentCount,
                onClick = onCommentClick,
            ),
        )
    }
}

@Composable
private fun MemberPostListContent(
    content: String,
    createdAt: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        ExpandableText(
            text = content,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = createdAt,
            color = SoomjaeTheme.colorScheme.text3,
            style = SoomjaeTheme.typography.captionS,
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
                    content = "안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.안녕하세요 반갑습니다.",
                    images = persistentListOf(),
                    likeCount = 12,
                    commentCount = 34,
                ),
                onMenuClick = { },
                onCommentClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun MemberPostListItemPreview2() {
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
                    content = "안녕하세요 반갑습니다.\n안녕하세요 반갑습니다.\n안녕하세요 반갑습니다.",
                    images = persistentListOf(),
                    likeCount = 12,
                    commentCount = 34,
                ),
                onMenuClick = { },
                onCommentClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
