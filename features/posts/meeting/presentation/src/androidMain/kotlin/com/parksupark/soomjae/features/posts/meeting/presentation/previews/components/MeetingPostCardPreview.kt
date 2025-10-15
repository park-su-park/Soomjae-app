package com.parksupark.soomjae.features.posts.meeting.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.components.MeetingPostCard
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import kotlinx.collections.immutable.ImmutableList

@PreviewLightDark
@Composable
private fun MeetingPostCardPreview(
    @PreviewParameter(MeetingPostUiPreviewParameterProvider::class) posts: ImmutableList<MeetingPostUi>,
) {
    AppTheme {
        MeetingPostCard(
            post = posts.first(),
            onFavoriteClick = { },
        )
    }
}
