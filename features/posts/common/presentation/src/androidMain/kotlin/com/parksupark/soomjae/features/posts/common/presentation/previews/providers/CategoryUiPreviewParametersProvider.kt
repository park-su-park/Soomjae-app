package com.parksupark.soomjae.features.posts.common.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.domain.post.model.Category
import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.post.model.toUi
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CategoryUiPreviewParameterData.categories
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class CategoryUiPreviewParametersProvider : PreviewParameterProvider<ImmutableList<CategoryUi>> {
    override val values: Sequence<ImmutableList<CategoryUi>> =
        sequenceOf(categories.map { it.toUi() }.toImmutableList())
}

object CategoryUiPreviewParameterData {
    val categories = persistentListOf(
        Category(
            id = 1L,
            name = "Android",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 2L,
            name = "iOS",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 3L,
            name = "Web",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 4L,
            name = "Backend",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 5L,
            name = "DevOps",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 6L,
            name = "AI",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 7L,
            name = "Cloud",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 8L,
            name = "Security",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 9L,
            name = "Data Science",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 10L,
            name = "Game Development",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
        Category(
            id = 11L,
            name = "UI/UX",
            hierarchy = 1,
            children = emptyList(),
            parentId = null,
        ),
    )
}
