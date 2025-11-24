package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.post.model.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.components.CommentItem
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CommentUiPreviewParameterProvider
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommentPreview(
    @PreviewParameter(CommentUiPreviewParameterProvider::class) comments: ImmutableList<CommentUi>,
) {
    AppTheme {
        SoomjaeSurface {
            CommentItem(
                comment = comments[0],
            )
        }
    }
}
