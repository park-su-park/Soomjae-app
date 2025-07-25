package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailScreen
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailState
import com.parksupark.soomjae.features.post.presentation.models.CommunityPostDetailUi
import com.parksupark.soomjae.features.post.presentation.previews.proviers.CommunityPostDetailUiPreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlin.time.ExperimentalTime

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
