package com.parksupark.soomjae.features.post.presentation.previews.proviers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.features.post.presentation.models.CommunityPostDetailUi
import com.parksupark.soomjae.features.post.presentation.models.toUi
import com.parksupark.soomjae.features.post.presentation.previews.proviers.CommentPreviewParameterData.comments
import com.parksupark.soomjae.features.post.presentation.previews.proviers.CommunityPostDetailUiPreviewParameterData.communityPostDetailUi
import com.parksupark.soomjae.features.post.presentation.previews.proviers.CommunityPostPreviewParameterData.communityPosts
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal class CommunityPostDetailUiPreviewParameterProvider : PreviewParameterProvider<ImmutableList<CommunityPostDetailUi>> {
    override val values: Sequence<ImmutableList<CommunityPostDetailUi>> = sequenceOf(communityPostDetailUi)
}

internal object CommunityPostDetailUiPreviewParameterData {
    @OptIn(ExperimentalTime::class)
    val communityPostDetailUi = persistentListOf(
        CommunityPostDetailUi(
            post = communityPosts[0].toUi(),
            isLiked = false,
            likeCount = 18,
            commentCount = comments.count(),
            comments = comments.map { it.toUi() }.toPersistentList(),
        ),
    )
}
