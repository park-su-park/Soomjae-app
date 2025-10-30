package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.modifiers.bottomBorder
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.PostWriter
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteDialogSelection
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputContent
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputTitle
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.community.presentation.resources.Res
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_write_button_confirm_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_write_navigate_up_description
import com.parksupark.soomjae.features.posts.community.presentation.resources.write_category_selection_label
import com.parksupark.soomjae.features.posts.community.presentation.resources.write_category_selection_placeholder
import com.parksupark.soomjae.features.posts.community.presentation.resources.write_location_selection_label
import com.parksupark.soomjae.features.posts.community.presentation.resources.write_location_selection_placeholder
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CommunityWriteScreen(
    state: CommunityWriteState,
    onAction: (CommunityWriteAction) -> Unit,
    snackbarHost: @Composable () -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            CommunityWriteTopBar(
                onBackClick = { onAction(CommunityWriteAction.OnBackClick) },
                onConfirmClick = { onAction(CommunityWriteAction.OnConfirmClick) },
                canSubmit = state.canSubmit,
            )
        },
        snackbarHost = snackbarHost,
    ) { innerPadding ->
        PostWriter(
            title = {
                WriteInputTitle(
                    state = state.inputTitle,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            },
            body = {
                WriteInputContent(
                    state = state.inputContent,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
            },
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(top = 12.dp),
            extras = {
                AdditionalInfoSelection(
                    categories = state.categories,
                    selectedCategory = state.selectedCategory,
                    onCategorySelect = { categoryId ->
                        onAction(CommunityWriteAction.OnCategorySelected(categoryId))
                    },
                    locations = state.locations,
                    selectedLocation = state.selectedLocation,
                    onLocationSelect = { locationCode ->
                        onAction(CommunityWriteAction.OnLocationSelected(locationCode))
                    },
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityWriteTopBar(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    canSubmit: Boolean,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        modifier = Modifier.bottomBorder(
            color = SoomjaeTheme.colorScheme.divider1,
            height = 1.dp,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.community_write_navigate_up_description.value,
                        tint = SoomjaeTheme.colorScheme.icon,
                    )
                },
            )
        },
        actions = {
            IconButton(
                onClick = onConfirmClick,
                enabled = canSubmit,
                content = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = Res.string.community_write_button_confirm_description.value,
                        tint = if (canSubmit) {
                            SoomjaeTheme.colorScheme.iconColored
                        } else {
                            SoomjaeTheme.colorScheme.iconDisabled
                        },
                    )
                },
            )
        },
    )
}

@Composable
private fun AdditionalInfoSelection(
    categories: ImmutableList<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelect: (categoryId: Long) -> Unit,
    locations: ImmutableList<LocationUi>,
    selectedLocation: LocationUi?,
    onLocationSelect: (locationCode: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        WriteDialogSelection(
            items = categories,
            label = Res.string.write_category_selection_label.value,
            placeHolder = Res.string.write_category_selection_placeholder.value,
            onItemSelect = { onCategorySelect(it.id) },
            selectedItem = selectedCategory,
            itemKey = { it.id },
            itemName = { it.name },
        )

        WriteDialogSelection(
            items = locations,
            label = Res.string.write_location_selection_label.value,
            placeHolder = Res.string.write_location_selection_placeholder.value,
            onItemSelect = { onLocationSelect(it.code) },
            selectedItem = selectedLocation,
            itemKey = { it.code },
            itemName = { it.name },
        )
    }
}

@Preview
@Composable
private fun CommunityWriteScreenPreview() {
    AppTheme {
        CommunityWriteScreen(
            state = CommunityWriteState(),
            onAction = { },
            snackbarHost = { },
        )
    }
}
