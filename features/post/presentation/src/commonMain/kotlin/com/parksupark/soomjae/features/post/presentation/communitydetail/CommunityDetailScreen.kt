package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.components.PostDetailAuthorHeader
import com.parksupark.soomjae.features.post.presentation.components.PostDetailTitleHeader
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.community_detail_comment_button_description
import com.parksupark.soomjae.features.post.presentation.resources.community_detail_dislike_button_description
import com.parksupark.soomjae.features.post.presentation.resources.community_detail_like_button_description
import com.parksupark.soomjae.features.post.presentation.resources.community_detail_navigate_up_description
import com.parksupark.soomjae.features.post.presentation.resources.community_detail_title

@Composable
internal fun CommunityDetailScreen(
    state: CommunityDetailState,
    onAction: (CommunityDetailAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = { CommunityDetailTopBar(onBackClick = { onAction(CommunityDetailAction.OnBackClick) }) },
    ) { innerPadding ->
        when (state) {
            is CommunityDetailState.Error -> {
                // Handle error state, e.g., show a snackbar or dialog
            }

            is CommunityDetailState.InitialLoading -> SoomjaeCircularProgressIndicator()

            is CommunityDetailState.Success -> PostContentScreen(
                state = state,
                onAction = onAction,
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            )
        }
    }
}

@Composable
private fun PostContentScreen(
    state: CommunityDetailState.Success,
    onAction: (CommunityDetailAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PostDetailAuthorHeader(
            author = state.postDetail.post.author,
            modifier = Modifier.fillMaxWidth(),
        )

        PostDetailTitleHeader(
            title = state.postDetail.post.title,
            createdAt = state.postDetail.post.formattedCreatedAt,
            modifier = Modifier.fillMaxWidth(),
        )

        PostContent(
            content = state.postDetail.post.content,
        )

        PostAdditionalButtons(
            isLiked = state.postDetail.isLiked,
            onToggleLikeClick = { onAction(CommunityDetailAction.OnToggleLikeClick) },
            likeCount = state.postDetail.likeCount,
            commentCount = state.postDetail.commentCount,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityDetailTopBar(onBackClick: () -> Unit) {
    SoomjaeTopAppBar(
        title = {
            Text(
                text = Res.string.community_detail_title.value,
                style = SoomjaeTheme.typography.labelL.copy(
                    color = SoomjaeTheme.colorScheme.text2,
                ),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.community_detail_navigate_up_description.value,
                        tint = SoomjaeTheme.colorScheme.text2,
                    )
                },
            )
        },
    )
}

@Composable
private fun PostContent(content: String) {
    Text(
        text = content,
        style = SoomjaeTheme.typography.body1.copy(
            color = SoomjaeTheme.colorScheme.text2,
        ),
    )
}

@Composable
private fun PostAdditionalButtons(
    isLiked: Boolean,
    onToggleLikeClick: () -> Unit,
    likeCount: Long,
    commentCount: Long,
) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        LikeButton(onToggleLikeClick = onToggleLikeClick, isLiked = isLiked, likeCount = likeCount)

        CommentButton(commentCount = commentCount)
    }
}

@Composable
private fun RowScope.LikeButton(
    onToggleLikeClick: () -> Unit,
    isLiked: Boolean,
    likeCount: Long,
) {
    Row(modifier = Modifier.weight(1f)) {
        IconButton(onClick = onToggleLikeClick) {
            if (isLiked) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = Res.string.community_detail_dislike_button_description.value,
                    tint = SoomjaeTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = Res.string.community_detail_like_button_description.value,
                )
            }
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
private fun RowScope.CommentButton(commentCount: Long) {
    Row(modifier = Modifier.weight(1f)) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = Res.string.community_detail_comment_button_description.value,
                tint = SoomjaeTheme.colorScheme.primary,
            )
        }

        Text(
            text = "$commentCount",
            style = SoomjaeTheme.typography.body2.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
    }
}
