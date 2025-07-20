package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.domain.models.Comment
import com.parksupark.soomjae.features.post.domain.models.Member
import com.parksupark.soomjae.features.post.presentation.components.CommentItem
import com.parksupark.soomjae.features.post.presentation.models.toUi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommentPreview() {
    AppTheme {
        SoomjaeSurface {
            CommentItem(
                comment = Comment(
                    id = 1,
                    content = "This is a sample comment.",
                    author = Member(
                        id = "1",
                        nickname = "John Doe",
                        profileImageUrl = "https://ui-avatars.com/api/?name=John+Doe",
                    ),
                    createdAt = Clock.System.now(),
                ).toUi(),
            )
        }
    }
}
