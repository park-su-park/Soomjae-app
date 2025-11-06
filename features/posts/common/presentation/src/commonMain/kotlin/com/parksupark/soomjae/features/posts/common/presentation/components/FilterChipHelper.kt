package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.filter_chip_category
import com.parksupark.soomjae.features.posts.common.presentation.resources.filter_chip_category_selected_multiple
import com.parksupark.soomjae.features.posts.common.presentation.resources.filter_chip_location
import com.parksupark.soomjae.features.posts.common.presentation.resources.filter_chip_location_selected_multiple
import kotlinx.collections.immutable.ImmutableCollection

@Composable
fun buildCategoryLabel(selected: ImmutableCollection<CategoryUi>): String = when (selected.size) {
    0 -> Res.string.filter_chip_category.value
    1 -> selected.first().name
    else -> Res.string.filter_chip_category_selected_multiple.value(
        selected.first().name,
        selected.size,
    )
}

@Composable
fun buildLocationLabel(selected: ImmutableCollection<LocationUi>): String = when (selected.size) {
    0 -> Res.string.filter_chip_location.value
    1 -> selected.first().name
    else -> Res.string.filter_chip_location_selected_multiple.value(
        selected.first().name,
        selected.size,
    )
}
