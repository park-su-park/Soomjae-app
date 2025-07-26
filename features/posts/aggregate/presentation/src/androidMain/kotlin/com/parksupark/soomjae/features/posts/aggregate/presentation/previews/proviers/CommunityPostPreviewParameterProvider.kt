package com.parksupark.soomjae.features.posts.aggregate.presentation.previews.proviers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.presentation.ui.previews.proviers.MemberPreviewParameterData
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class CommunityPostPreviewParameterProvider : PreviewParameterProvider<ImmutableList<CommunityPost>> {
    override val values: Sequence<ImmutableList<CommunityPost>> =
        sequenceOf(CommunityPostPreviewParameterData.communityPosts.toImmutableList())
}

object CommunityPostPreviewParameterData {
    @OptIn(ExperimentalTime::class)
    val communityPosts = listOf(
        CommunityPost(
            id = 1,
            title = "Show off your neighborhood cat photos",
            content = "This is a stray cat in my neighborhood, and it's so cute that I'm sharing a photo. \nI see it every day, and my heart aches every time I see it.",
            author = MemberPreviewParameterData.members[0],
            createdAt = Instant.parse("2023-11-16T12:00:00Z"),
        ),
        CommunityPost(
            id = 2,
            title = "Soomgo User Community Guidelines",
            content = "Please be sure to check the Soomgo User Community Guidelines. \nLet's create a community where we care for and respect each other.",
            author = MemberPreviewParameterData.members[1],
            createdAt = Instant.parse("2023-11-15T10:30:00Z"),
        ),
        CommunityPost(
            id = 3,
            title = "New Feature Update Announcement",
            content = "New features have been added to the Soomgo app! \nCheck them out now. \n\n- Feature 1: ...\n- Feature 2: ...",
            author = MemberPreviewParameterData.members[2],
            createdAt = Instant.parse("2023-11-14T18:45:00Z"),
        ),
        CommunityPost(
            id = 4,
            title = "Anyone want to study together this weekend?",
            content = "Is anyone interested in studying coding together at a cafe this weekend? \nPlease leave a comment if you're interested!",
            author = MemberPreviewParameterData.members[0],
            createdAt = Instant.parse("2023-11-13T09:15:00Z"),
        ),
    )
}
