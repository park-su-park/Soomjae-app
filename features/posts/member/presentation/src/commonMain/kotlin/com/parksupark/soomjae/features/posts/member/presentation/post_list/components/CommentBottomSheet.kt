package com.parksupark.soomjae.features.posts.member.presentation.post_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.composables.core.DragIndication
import com.composables.core.ModalBottomSheet
import com.composables.core.ModalBottomSheetScope
import com.composables.core.ModalBottomSheetState
import com.composables.core.Scrim
import com.composables.core.Sheet
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.post.model.AuthorUi
import com.parksupark.soomjae.core.presentation.ui.post.model.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentInputBar
import com.parksupark.soomjae.features.posts.member.presentation.post_list.MemberPostListAction
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CommentBottomSheet(
    state: ModalBottomSheetState,
    onAction: (MemberPostListAction) -> Unit,
    onDismiss: () -> Unit,
    commentState: MemberPostCommentState,
) {
    ModalBottomSheet(
        state = state,
        onDismiss = onDismiss,
    ) {
        Scrim()

        CommentBottomSheetContent(
            commentState = commentState,
            onAction = onAction,
        )
    }
}

@Composable
private fun ModalBottomSheetScope.CommentBottomSheetContent(
    commentState: MemberPostCommentState,
    onAction: (MemberPostListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.statusBarsPadding()
            .padding(
                WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal).asPaddingValues(),
            ),
    ) {
        Sheet(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            backgroundColor = SoomjaeTheme.colorScheme.background1,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DragIndication(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(SoomjaeTheme.colorScheme.text3, RoundedCornerShape(100))
                        .size(width = 40.dp, height = 5.dp),
                )

                CommentHeader(commentCount = commentState.comments.size)

                CommentList(commentState.comments)

                CommentBar(
                    state = commentState.inputComments,
                    canSubmit = !commentState.isCommentSubmitting,
                    onSendClick = {
                        onAction(MemberPostListAction.OnSubmitCommentClick)
                    },
                )
            }
        }
    }
}

@Composable
private fun CommentHeader(commentCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SoomjaeTheme.typography.labelL.toSpanStyle()) {
                    append("댓글 ")
                }
                withStyle(
                    SoomjaeTheme.typography.captionL.copy(
                        color = SoomjaeTheme.colorScheme.primary,
                    ).toSpanStyle(),
                ) {
                    append("$commentCount")
                }
            },
        )
    }
}

@Composable
private fun ColumnScope.CommentList(comments: ImmutableList<CommentUi>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items = comments, key = { it.id }) { comment ->
            CommentListItem(
                comment = comment,
                onLikeClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
            )
        }
    }
}

@Composable
private fun CommentBar(
    state: TextFieldState,
    canSubmit: Boolean,
    onSendClick: () -> Unit,
) {
    CommentInputBar(
        state = state,
        onSendClick = onSendClick,
        canSubmit = canSubmit,
    )
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommentBottomSheetPreview() {
    val state = rememberModalBottomSheetState(
        initialDetent = SheetDetent.FullyExpanded,
    )

    AppTheme(isSystemDarkTheme = false) {
        CommentBottomSheet(
            state = state,
            onAction = { },
            onDismiss = { },
            commentState = MemberPostCommentState(
                isLoading = false,
                comments = List(100) {
                    CommentUi(
                        id = it.toLong(),
                        content = "Sample comment content number $it",
                        author = AuthorUi(
                            id = it.toString(),
                            nickname = "User$it",
                            profileImageUrl = "",
                        ),
                        createdAt = Clock.System.now(),
                    )
                }.toPersistentList(),
            ),
        )
    }
}
