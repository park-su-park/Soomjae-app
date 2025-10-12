package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeVerticalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.like
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentBar
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentItem
import com.parksupark.soomjae.features.posts.common.presentation.components.PostDetailTitleHeader
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingAuthorUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingReviewUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_comment_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_dislike_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_join_meeting_button_text
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_leave_meeting_button_text
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_like_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_navigate_up_description

// TODO: extract string resources
@Composable
internal fun MeetingDetailScreen(
    state: MeetingDetailState,
    onAction: (MeetingDetailAction) -> Unit,
) {
    when (state) {
        MeetingDetailState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            SoomjaeCircularProgressIndicator()
        }

        is MeetingDetailState.Success ->
            SoomjaeScaffold(
                topBar = { MeetingDetailTopBar(onBackClick = { onAction(MeetingDetailAction.OnBackClick) }) },
                bottomBar = {
                    MeetingDetailBottomBar(
                        commentState = state.inputCommentState,
                        onSendClick = { onAction(MeetingDetailAction.OnSendCommentClick) },
                    )
                },
            ) { innerPadding ->
                MeetingDetailContent(
                    state = state,
                    contentPadding = innerPadding,
                    onToggleLikeClick = { onAction(MeetingDetailAction.OnToggleLikeClick) },
                    onToggleParticipationClick = { onAction(MeetingDetailAction.OnToggleParticipationClick) },
                    onMessageClick = { onAction(MeetingDetailAction.OnMessageClick) },
                )
            }
    }
}

@Composable
private fun MeetingDetailContent(
    state: MeetingDetailState.Success,
    contentPadding: PaddingValues,
    onToggleLikeClick: () -> Unit,
    onToggleParticipationClick: () -> Unit,
    onMessageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val post = state.postDetail.post

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
    ) {
        item {
            PostDetailTitleHeader(
                title = post.title,
                createdAt = post.formattedCreatedAt,
                modifier = Modifier.fillMaxWidth(),
            )

            PostDetailContent(
                content = post.content,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
            )

            PostDetailMeetingContent(
                currentParticipantCount = state.postDetail.currentParticipantCount,
                maxParticipantCount = state.postDetail.maxParticipationCount,
                recruitmentEndTime = state.postDetail.recruitmentPeriod.formattedEndTime,
                isUserJoined = state.isUserJoined,
                onToggleParticipationClick = onToggleParticipationClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )

            PostDetailAuthorHeader(
                meetingAuthor = MeetingAuthorUi(
                    author = post.author,
                    review = MeetingReviewUi(rating = 4.5f, reviewCount = 10),
                ),
                onMessageClick = onMessageClick,
                modifier = Modifier.fillMaxWidth(),
            )

            PostAdditionalButtons(
                isLiked = state.postDetail.isLike,
                onToggleLikeClick = onToggleLikeClick,
                likeCount = state.postDetail.post.likeCount,
                commentCount = state.postDetail.post.commentCount,
            )
        }

        items(items = state.postDetail.comments, key = { it.id }) { comment ->
            CommentItem(
                comment = comment,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PostDetailAuthorHeader(
    meetingAuthor: MeetingAuthorUi,
    onMessageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val author = meetingAuthor.author
    val review = meetingAuthor.review

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageRequest { data(author.profileImageUrl) },
            contentDescription = null,
            modifier = Modifier.size(48.dp),
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = author.nickname, maxLines = 1, style = SoomjaeTheme.typography.labelL)

            if (review != null) {
                Text(
                    text = "${review.rating} (${review.reviewCount})",
                    color = SoomjaeTheme.colorScheme.text3,
                    style = SoomjaeTheme.typography.captionM,
                )
            }
        }

        IconButton(
            onClick = onMessageClick,
            modifier = Modifier.background(
                color = SoomjaeTheme.colorScheme.background2,
                shape = RoundedCornerShape(24.dp),
            ),
            content = {
                Icon(
                    imageVector = Icons.Outlined.Mail,
                    contentDescription = "메시지 보내기",
                    tint = SoomjaeTheme.colorScheme.icon,
                )
            },
        )
    }
}

@Composable
private fun PostDetailMeetingContent(
    currentParticipantCount: Long,
    maxParticipantCount: Long,
    recruitmentEndTime: String,
    isUserJoined: Boolean,
    onToggleParticipationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "참여자 수",
                modifier = Modifier.height(IntrinsicSize.Max),
                tint = SoomjaeTheme.colorScheme.icon,
            )
            Text(
                text = "참여자: $currentParticipantCount / $maxParticipantCount",
                style = SoomjaeTheme.typography.body2.copy(
                    color = SoomjaeTheme.colorScheme.text2,
                ),
                modifier = Modifier.padding(start = 8.dp),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "마감 시간",
                modifier = Modifier.height(IntrinsicSize.Max),
                tint = SoomjaeTheme.colorScheme.icon,
            )
            Text(
                text = "신청 마감: $recruitmentEndTime",
                style = SoomjaeTheme.typography.body2.copy(
                    color = SoomjaeTheme.colorScheme.text2,
                ),
                modifier = Modifier.padding(start = 8.dp),
            )
        }

        if (isUserJoined) {
            SoomjaeSecondaryButton(
                onClick = onToggleParticipationClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Text(text = Res.string.meeting_detail_leave_meeting_button_text.value)
            }
        } else {
            SoomjaeButton(
                onClick = onToggleParticipationClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Text(text = Res.string.meeting_detail_join_meeting_button_text.value)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeetingDetailTopBar(onBackClick: () -> Unit) {
    SoomjaeTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.meeting_detail_navigate_up_description.value,
                    )
                },
            )
        },
    )
}

@Composable
private fun MeetingDetailBottomBar(
    commentState: TextFieldState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommentBar(
        state = commentState,
        onSendClick = onSendClick,
        modifier = modifier.fillMaxWidth().padding(4.dp),
    )
}

@Composable
private fun PostDetailContent(
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(content)
    }
}

@Composable
private fun PostAdditionalButtons(
    isLiked: Boolean,
    onToggleLikeClick: () -> Unit,
    likeCount: Int,
    commentCount: Int,
) {
    SoomjaeHorizontalDivider()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LikeButton(onToggleLikeClick = onToggleLikeClick, isLiked = isLiked, likeCount = likeCount)
        SoomjaeVerticalDivider(modifier = Modifier.padding(vertical = 16.dp))
        CommentButton(commentCount = commentCount)
    }
    SoomjaeHorizontalDivider()
}

@Composable
private fun RowScope.LikeButton(
    onToggleLikeClick: () -> Unit,
    isLiked: Boolean,
    likeCount: Int,
) {
    Row(
        modifier = Modifier.heightIn(min = 48.dp)
            .weight(1f)
            .clickable {
                onToggleLikeClick()
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isLiked) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = Res.string.meeting_detail_dislike_button_description.value,
                tint = SoomjaeTheme.colorScheme.like,
            )
        } else {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = Res.string.meeting_detail_like_button_description.value,
            )
        }

        Text(
            text = "$likeCount",
            style = SoomjaeTheme.typography.body2.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
    }
}

@Composable
private fun RowScope.CommentButton(commentCount: Int) {
    Row(
        modifier = Modifier.heightIn(min = 48.dp)
            .weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Comment,
            contentDescription = Res.string.meeting_detail_comment_button_description.value,
        )

        Text(
            text = "$commentCount",
            style = SoomjaeTheme.typography.body2.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
    }
}
