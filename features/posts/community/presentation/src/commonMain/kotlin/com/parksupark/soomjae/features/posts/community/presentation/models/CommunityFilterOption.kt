package com.parksupark.soomjae.features.posts.community.presentation.models

import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.post.model.LocationUi
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

data class CommunityFilterOption(
    val categories: ImmutableSet<CategoryUi> = persistentSetOf(),
    val locations: ImmutableSet<LocationUi> = persistentSetOf(),
)
