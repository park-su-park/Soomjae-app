package com.parksupark.soomjae.features.posts.meeting.presentation.tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.posts.common.presentation.components.PostActionItem
import com.parksupark.soomjae.features.posts.common.presentation.components.PostCard
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionType
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.RecruitmentPeriodUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MeetingPostCard(
    post: MeetingPostUi,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PostCard(
        title = { PostCardTitle(title = post.title) },
        modifier = modifier.background(SoomjaeTheme.colorScheme.background1),
        header = {
            PostAuthorHeader(
                author = post.author,
                subtitle = post.formattedCreatedAt,
            )
        },
        aboveHeader = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                when {
                    post.isFull -> RecruitmentStatusChip(status = RecruitmentStatus.FULL)
                    post.isExpired -> RecruitmentStatusChip(status = RecruitmentStatus.EXPIRED)
                    else -> RecruitmentStatusChip(status = RecruitmentStatus.RECRUITING)
                }
                MeetingPostCardChip(
                    text = "${post.currentParticipantCount}/${post.maxParticipantCount}",
                )
                if (post.category != null) {
                    MeetingPostCardChip(text = post.category.name)
                }
            }
        },
        body = {
            PostCardContent(contentPreview = post.content)
        },
        footer = {
            AdditionalActions(
                isUserLiked = post.isUserLiked,
                likeCount = post.likeCount.toLong(),
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
                modifier = Modifier.size(36.dp).clip(
                    RoundedCornerShape(12.dp),
                ),
                contentScale = ContentScale.Crop,
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
                    imageVector = Icons.Default.MoreVert,
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

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun MeetingPostCardPreview() {
    AppTheme {
        MeetingPostCard(
            post = MeetingPostUi(
                id = 1L,
                title = "안녕하세요! 같이 스터디하실 분 구합니다.",
                content = "저는 컴퓨터 공학과 3학년에 재학 중인 학생입니다. 이번 학기에 데이터베이스와 운영체제 수업을 듣고 있는데, " +
                    "혼자 공부하기가 힘들어서 같이 스터디할 분을 찾고 있습니다. 관심 있으신 분들은 연락 주세요!",
                createdAt = Clock.System.now(),
                author = AuthorUi(
                    id = "1L",
                    nickname = "홍길동",
                    profileImageUrl = "",
                ),
                isUserLiked = true,
                likeCount = 32,
                commentCount = 128,
                category = CategoryUi(id = 1L, name = "스터디"),
                maxParticipantCount = 4,
                currentParticipantCount = 2,
                recruitmentPeriod = RecruitmentPeriodUi(
                    startTime = Clock.System.now(),
                    endTime = Clock.System.now().plus(5.days),
                ),
            ),
            onFavoriteClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
