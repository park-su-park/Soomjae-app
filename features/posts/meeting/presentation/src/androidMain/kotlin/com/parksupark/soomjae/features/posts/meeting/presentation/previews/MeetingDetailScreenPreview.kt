package com.parksupark.soomjae.features.posts.meeting.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailState
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingPostDetailUi
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostDetailUiPreviewParameterProvider

@Composable
@Preview
private fun MeetingDetailScreenPreview(
    @PreviewParameter(MeetingPostDetailUiPreviewParameterProvider::class) postUi: MeetingPostDetailUi,
) {
    AppTheme {
        MeetingDetailScreen(
            state = MeetingDetailState.Success(postUi),
            onAction = { },
        )
    }
}
