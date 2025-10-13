package com.parksupark.soomjae.features.posts.community.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.presentation.ui.previews.proviers.MemberPreviewParameterData
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.models.toUi
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class CommunityPostUiPreviewParameterProvider : PreviewParameterProvider<ImmutableList<CommunityPostUi>> {
    override val values: Sequence<ImmutableList<CommunityPostUi>> =
        sequenceOf(CommunityPostPreviewParameterData.communityPosts.map { it.toUi() }.toImmutableList())
}

object CommunityPostPreviewParameterData {
    @OptIn(ExperimentalTime::class)
    val communityPosts = persistentListOf(
        CommunityPost(
            id = 1,
            title = "Show off your neighborhood cat photos",
            content = "This is a stray cat in my neighborhood, and it's so cute that I'm sharing a photo." +
                "I see it every day, and my heart aches every time I see it.",
            author = MemberPreviewParameterData.members[0],
            createdAt = Instant.parse("2023-11-16T12:00:00Z"),
            categoryName = "Daily",
            locationName = "Gangnam-gu",
            likeCount = 12,
            isUserLiked = true,
        ),
        CommunityPost(
            id = 2,
            title = "Soomgo User Community Guidelines",
            content = "Please be sure to check the Soomgo User Community Guidelines." +
                "Let 's create a community where we care for and respect each other.",
            author = MemberPreviewParameterData.members[1],
            createdAt = Instant.parse("2023-11-15T10:30:00Z"),
            categoryName = "Notice",
            locationName = null,
            likeCount = 5,
            isUserLiked = false,
        ),
        CommunityPost(
            id = 3,
            title = "New Feature Update Announcement",
            content = "New features have been added to the Soomgo app!" +
                "Check them out now.- Feature 1: ..." +
                "- Feature 2: ...",
            author = MemberPreviewParameterData.members[2],
            createdAt = Instant.parse("2023-11-14T18:45:00Z"),
            categoryName = "Notice",
            locationName = null,
            likeCount = 20,
            isUserLiked = true,
        ),
        CommunityPost(
            id = 4,
            title = "Anyone want to study together this weekend?",
            content = "Is anyone interested in studying coding together at a cafe this weekend?" +
                "Please leave a comment if you" +
                "'re interested!",
            author = MemberPreviewParameterData.members[0],
            createdAt = Instant.parse("2023-11-13T09:15:00Z"),
            categoryName = "Study",
            locationName = "Seocho-gu",
            likeCount = 8,
            isUserLiked = false,
        ),
    )
}
