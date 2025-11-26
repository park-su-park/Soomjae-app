package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeVerticalDivider
import com.parksupark.soomjae.core.presentation.designsystem.modifiers.bottomBorder
import com.parksupark.soomjae.core.presentation.designsystem.modifiers.topBorder
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.post.components.PostActionItem
import com.parksupark.soomjae.core.presentation.ui.post.model.PostActionType
import com.parksupark.soomjae.core.presentation.ui.post.model.PostActionUi
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentInputBar
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentItem
import com.parksupark.soomjae.features.posts.common.presentation.components.PostDetailAuthorHeader
import com.parksupark.soomjae.features.posts.common.presentation.components.PostDetailTitleHeader
import com.parksupark.soomjae.features.posts.community.presentation.resources.Res
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_detail_comment_button_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_detail_delete_button_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_detail_edit_button_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_detail_navigate_up_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_detail_title

@Composable
internal fun CommunityDetailScreen(
    state: CommunityDetailState,
    onAction: (CommunityDetailAction) -> Unit,
) {
    when (state) {
        is CommunityDetailState.Error -> {
            // Handle error state, e.g., show a snackbar or dialog
        }

        is CommunityDetailState.InitialLoading -> Box(modifier = Modifier.fillMaxSize()) {
            SoomjaeCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        is CommunityDetailState.Success -> CommunityDetailSuccessScreen(state, onAction)
    }
}

@Composable
private fun CommunityDetailSuccessScreen(
    state: CommunityDetailState.Success,
    onAction: (CommunityDetailAction) -> Unit,
) {
    var inputBarHeight by remember { mutableStateOf(0.dp) }

    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommunityDetailTopBar(
                isMine = state.isMine,
                onBackClick = { onAction(CommunityDetailAction.OnBackClick) },
                onDeleteClick = { onAction(CommunityDetailAction.OnDeleteClick) },
                onEditClick = { onAction(CommunityDetailAction.OnEditClick) },
            )
        },
    ) { innerPadding ->
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .imePadding()
                    .padding(bottom = inputBarHeight),
                contentPadding = innerPadding,
            ) {
                item {
                    PostContentScreen(
                        state = state,
                        onAction = onAction,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                items(items = state.postDetail.comments, key = { it.id }) { comment ->
                    CommentItem(comment = comment, modifier = Modifier.fillMaxWidth())
                }
            }

            val localDensity = LocalDensity.current
            CommentInputBar(
                state = state.inputCommentState,
                onSendClick = { onAction(CommunityDetailAction.OnSendCommentClick) },
                modifier = Modifier.align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .onSizeChanged {
                        inputBarHeight = with(localDensity) { it.height.toDp() }
                    },
                isLoggedIn = state.isLoggedIn,
                onLoginRequest = { onAction(CommunityDetailAction.LoginRequest) },
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
private fun CommunityDetailTopBar(
    isMine: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
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
                        tint = SoomjaeTheme.colorScheme.icon,
                    )
                },
            )
        },
        actions = {
            if (isMine) {
                CommunityDetailScreenActions(
                    onDeleteClick = onDeleteClick,
                    onEditClick = onEditClick,
                )
            }
        },
    )
}

@Composable
private fun RowScope.CommunityDetailScreenActions(
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    IconButton(
        onClick = onDeleteClick,
        content = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = Res.string.community_detail_delete_button_description.value,
                tint = SoomjaeTheme.colorScheme.icon,
            )
        },
    )
    IconButton(
        onClick = onEditClick,
        content = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = Res.string.community_detail_edit_button_description.value,
                tint = SoomjaeTheme.colorScheme.icon,
            )
        },
    )
}

@Composable
private fun PostContent(content: String) {
    Text(
        text = content,
        modifier = Modifier.padding(16.dp),
        style = SoomjaeTheme.typography.body1.copy(
            color = SoomjaeTheme.colorScheme.text2,
        ),
    )
}

@Composable
private fun PostAdditionalButtons(
    isLiked: Boolean,
    onToggleLikeClick: () -> Unit,
    likeCount: Int,
    commentCount: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth().topBorder(
            color = SoomjaeTheme.colorScheme.divider1,
            height = 1.dp,
        ).bottomBorder(
            color = SoomjaeTheme.colorScheme.divider1,
            height = 1.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LikeButton(onToggleLikeClick = onToggleLikeClick, isLiked = isLiked, likeCount = likeCount)
        SoomjaeVerticalDivider(modifier = Modifier.width(1.dp).height(IntrinsicSize.Max))
        CommentButton(commentCount = commentCount)
    }
}

@Composable
private fun RowScope.LikeButton(
    onToggleLikeClick: () -> Unit,
    isLiked: Boolean,
    likeCount: Int,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier.heightIn(min = 48.dp).weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onToggleLikeClick()
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PostActionItem(
            action = PostActionUi(
                type = PostActionType.Like,
                isSelected = isLiked,
                count = likeCount.toLong(),
            ),
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun RowScope.CommentButton(commentCount: Int) {
    Row(
        modifier = Modifier.heightIn(min = 48.dp).weight(1f),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Comment,
            contentDescription = Res.string.community_detail_comment_button_description.value,
        )

        Text(
            text = "$commentCount",
            style = SoomjaeTheme.typography.body2.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
    }
}
