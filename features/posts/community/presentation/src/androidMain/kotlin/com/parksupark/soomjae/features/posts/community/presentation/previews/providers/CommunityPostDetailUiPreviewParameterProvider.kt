package com.parksupark.soomjae.features.posts.community.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CommentPreviewParameterData
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostDetailUi
import com.parksupark.soomjae.features.posts.community.presentation.models.toUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

class CommunityPostDetailUiPreviewParameterProvider :
    PreviewParameterProvider<ImmutableList<CommunityPostDetailUi>> {
    override val values: Sequence<ImmutableList<CommunityPostDetailUi>> = sequenceOf(
        CommunityPostDetailUiPreviewParameterData.communityPostDetailUi,
    )
}

internal object CommunityPostDetailUiPreviewParameterData {
    @OptIn(ExperimentalTime::class)
    val communityPostDetailUi = persistentListOf(
        CommunityPostDetailUi(
            post = CommunityPostPreviewParameterData.communityPosts[0].toUi(),
            isLiked = false,
            likeCount = 18,
            commentCount = CommentPreviewParameterData.comments.count(),
            comments = CommentPreviewParameterData.comments.map { it.toUi() }.toPersistentList(),
        ),
    )
}
