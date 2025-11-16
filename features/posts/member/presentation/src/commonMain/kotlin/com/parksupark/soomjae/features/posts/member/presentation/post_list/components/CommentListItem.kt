package com.parksupark.soomjae.features.posts.member.presentation.post_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.features.posts.common.presentation.components.PostActionItem
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionType
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionUi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommentListItem(
    comment: CommentUi,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val author = comment.author

    SoomjaeSurface {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ProfileImage(
                imageUrl = author.profileImageUrl,
                size = 36.dp,
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = author.nickname,
                        style = SoomjaeTheme.typography.labelM,
                    )
                    Text(
                        text = comment.formattedCreatedAt,
                        color = SoomjaeTheme.colorScheme.text3,
                        style = SoomjaeTheme.typography.captionS,
                    )
                }

                Text(
                    text = comment.content,
                    style = SoomjaeTheme.typography.captionM,
                )
            }

            Column(
                modifier = Modifier.height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.Center,
            ) {
                PostActionItem(
                    action = PostActionUi(
                        type = PostActionType.Like,
                        count = null, // TODO: 좋아요 개수
                        onClick = onLikeClick,
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommentListItemPreview() {
    AppTheme {
        CommentListItem(
            comment = CommentUi(
                id = 0,
                content = "우와 정말 유용한 정보네요! 저도 한번 시도해봐야겠어요. 혹시 추가적인 팁이 있다면 알려주실 수 있나요?",
                author = AuthorUi(id = "", nickname = "숨숨숨", profileImageUrl = null),
                createdAt = Clock.System.now().minus(5.minutes),
            ),
            onLikeClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
