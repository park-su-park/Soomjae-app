package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeVerticalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.like
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentItem
import com.parksupark.soomjae.features.posts.common.presentation.components.PostDetailAuthorHeader
import com.parksupark.soomjae.features.posts.common.presentation.components.PostDetailTitleHeader
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_comment_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_dislike_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_like_button_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_navigate_up_description

@Composable
internal fun MeetingDetailScreen(
    state: MeetingDetailState,
    onAction: (MeetingDetailAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { MeetingDetailTopBar(onBackClick = { onAction(MeetingDetailAction.OnBackClick) }) },
    ) { innerPadding ->
        when (state) {
            MeetingDetailState.Loading -> {
                Spacer(modifier = Modifier.padding(innerPadding).height(24.dp))
                SoomjaeCircularProgressIndicator()
            }

            is MeetingDetailState.Success ->
                MeetingDetailContent(
                    state = state,
                    contentPadding = innerPadding,
                    onToggleLikeClick = { onAction(MeetingDetailAction.OnToggleLikeClick) },
                )
        }
    }
}

@Composable
private fun MeetingDetailContent(
    state: MeetingDetailState.Success,
    contentPadding: PaddingValues,
    onToggleLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val post = state.postDetail.post

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
    ) {
        item {
            PostDetailAuthorHeader(
                author = post.author,
                modifier = Modifier.fillMaxWidth(),
            )

            PostDetailTitleHeader(
                title = post.title,
                createdAt = post.formattedCreatedAt,
                modifier = Modifier.fillMaxWidth(),
            )

            PostDetailContent(
                content = post.content,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
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
