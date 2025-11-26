package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.core.presentation.ui.post.model.AuthorUi
import com.parksupark.soomjae.core.presentation.ui.post.model.CommentUi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommentItem(
    comment: CommentUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.background(color = SoomjaeTheme.colorScheme.background1).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProfileImage(
            imageUrl = comment.author.profileImageUrl,
            size = 40.dp,
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = comment.author.nickname, style = SoomjaeTheme.typography.labelM)

            Text(text = comment.content, style = SoomjaeTheme.typography.body1)

            Text(
                text = comment.formattedCreatedAt,
                style = SoomjaeTheme.typography.captionM.copy(
                    color = SoomjaeTheme.colorScheme.text3,
                ),
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommentItemPreview() {
    AppTheme {
        CommentItem(
            comment = CommentUi(
                id = 1L,
                content = "This is a sample comment for preview purposes.",
                author = AuthorUi(
                    id = "user123",
                    nickname = "SampleUser",
                    profileImageUrl = "https://via.placeholder.com/150",
                ),
                createdAt = Clock.System.now(),
            ),
        )
    }
}
