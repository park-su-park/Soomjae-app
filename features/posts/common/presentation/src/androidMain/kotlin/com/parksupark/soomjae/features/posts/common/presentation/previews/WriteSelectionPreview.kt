package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteDialogSelection
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CategoryUiPreviewParametersProvider
import kotlinx.collections.immutable.ImmutableList

@Preview
@Composable
private fun WriteSelectionPreview(
    @PreviewParameter(CategoryUiPreviewParametersProvider::class) categories:
        ImmutableList<CategoryUi>,
) {
    AppTheme {
        SoomjaeSurface {
            WriteDialogSelection(
                items = categories,
                label = "카테고리",
                placeHolder = "",
                onItemSelect = { },
                selectedItem = categories[0],
                itemKey = { it.id },
                itemName = { it.name },
            )
        }
    }
}
