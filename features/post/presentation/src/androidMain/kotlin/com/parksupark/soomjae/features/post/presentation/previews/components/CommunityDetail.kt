package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailScreen
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailState
import com.parksupark.soomjae.features.post.presentation.models.AuthorUi
import com.parksupark.soomjae.features.post.presentation.models.CommunityPostDetailUi
import com.parksupark.soomjae.features.post.presentation.models.CommunityPostUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalTime::class)
@Composable
@Preview(name = "CommunityDetail")
private fun CommunityDetailScreenPreview() {
    AppTheme {
        CommunityDetailScreen(
            state = CommunityDetailState.Success(
                postDetail = CommunityPostDetailUi(
                    post = CommunityPostUi(
                        id = 1,
                        title = "Sample Post Title",
                        content = "This is a sample post content for preview purposes.",
                        author = AuthorUi(
                            id = "author1",
                            nickname = "John Doe",
                            profileImageUrl = "https://example.com/profile.jpg",
                        ),
                        createdAt = LocalDate(2023, 10, 1).atStartOfDayIn(TimeZone.currentSystemDefault()),
                    ),
                    isLiked = false,
                    likeCount = 100,
                    commentCount = 100,
                    comments = persistentListOf(),
                ),
            ),
            onAction = {},
        )
    }
}
