package com.parksupark.soomjae.features.posts.member.presentation.post_list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentState
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CommentBottomSheetScreen(
    state: MemberPostCommentState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items = state.comments, key = { it.id }) { comment ->
            CommentListItem(
                comment = comment,
                onLikeClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun CommentBar(modifier: Modifier = Modifier) {
    // TODO: implement comment input bar
}

@Preview
@Composable
private fun CommentBottomSheetScreenPreview() {
    AppTheme {
        CommentBottomSheetScreen(
            state = MemberPostCommentState(
                isLoading = false,
                comments = persistentListOf(),
            ),
        )
    }
}
