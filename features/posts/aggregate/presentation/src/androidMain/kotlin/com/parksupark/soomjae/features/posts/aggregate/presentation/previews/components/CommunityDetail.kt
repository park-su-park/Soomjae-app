package com.parksupark.soomjae.features.posts.aggregate.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.aggregate.presentation.previews.proviers.CommunityPostDetailUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailScreen
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailState
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostDetailUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalTime::class)
@Composable
@Preview(name = "CommunityDetail")
private fun CommunityDetailScreenPreview(
    @PreviewParameter(CommunityPostDetailUiPreviewParameterProvider::class) postDetails: ImmutableList<CommunityPostDetailUi>,
) {
    AppTheme {
        CommunityDetailScreen(
            state = CommunityDetailState.Success(
                postDetail = postDetails[0],
            ),
            onAction = {},
        )
    }
}
