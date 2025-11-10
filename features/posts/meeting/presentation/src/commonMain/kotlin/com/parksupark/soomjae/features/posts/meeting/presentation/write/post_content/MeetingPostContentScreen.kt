package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.PostWriteLayout
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteDialogSelection
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputContent
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputTitle
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingFormUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_button_confirm_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_category_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_category_placeholder
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_location_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_location_placeholder
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_navigate_up_description
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.components.MeetingDateForm
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MeetingPostContentScreen(
    state: MeetingPostContentState,
    onAction: (MeetingPostWriteAction) -> Unit,
    snackbarHost: @Composable () -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            MeetingWriteTopBar(
                onBackClick = { onAction(MeetingPostWriteAction.OnBackClick) },
                onConfirmClick = { onAction(MeetingPostWriteAction.OnSubmitClick) },
                canSubmit = state.canSubmit,
            )
        },
        snackbarHost = snackbarHost,
    ) { innerPadding ->
        PostWriteLayout(
            title = {
                WriteInputTitle(
                    state = state.inputTitle,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            },
            body = {
                WriteInputContent(
                    state = state.inputContent,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            },
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            extras = {
                AdditionalInfoSelection(
                    state = state,
                    onAction = onAction,
                    categories = state.categories,
                    selectedCategory = state.selectedCategory,
                    onCategorySelect = { onAction(MeetingPostWriteAction.OnCategorySelect(it)) },
                    locations = state.locations,
                    selectedLocation = state.selectedLocation,
                    onLocationSelect = { onAction(MeetingPostWriteAction.OnLocationSelect(it)) },
                )
            },
            scrollable = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MeetingWriteTopBar(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    canSubmit: Boolean,
) {
    val animateSubmitColor by animateColorAsState(
        targetValue = if (canSubmit) {
            SoomjaeTheme.colorScheme.iconColored
        } else {
            SoomjaeTheme.colorScheme.iconDisabled
        },
        label = "AnimateSubmitColor",
    )

    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                SoomjaeIcon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = Res.string.meeting_write_navigate_up_description.value,
                )
            }
        },
        actions = {
            IconButton(
                onClick = onConfirmClick,
                enabled = canSubmit,
            ) {
                SoomjaeIcon(
                    imageVector = Icons.Default.Check,
                    contentDescription = Res.string.meeting_write_button_confirm_description.value,
                    tint = animateSubmitColor,
                )
            }
        },
    )
}

@Composable
private fun AdditionalInfoSelection(
    state: MeetingPostContentState,
    onAction: (MeetingPostWriteAction) -> Unit,
    categories: ImmutableList<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelect: (categoryId: Long) -> Unit,
    locations: ImmutableList<LocationUi>,
    selectedLocation: LocationUi?,
    onLocationSelect: (locationCode: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            CategoryLocationSelection(
                categories = categories,
                onCategorySelect = onCategorySelect,
                selectedCategory = selectedCategory,
                locations = locations,
                onLocationSelect = onLocationSelect,
                selectedLocation = selectedLocation,
            )
            SoomjaeHorizontalDivider()
        }

        item {
            MeetingFormSelection(
                form = state.meetingForm,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth(),
            )
            SoomjaeHorizontalDivider()
        }
    }
}

@Composable
private fun CategoryLocationSelection(
    categories: ImmutableList<CategoryUi>,
    onCategorySelect: (Long) -> Unit,
    selectedCategory: CategoryUi?,
    locations: ImmutableList<LocationUi>,
    onLocationSelect: (Long) -> Unit,
    selectedLocation: LocationUi?,
) {
    Column {
        SoomjaeHorizontalDivider()
        WriteDialogSelection(
            items = categories,
            label = Res.string.meeting_write_category_label.value,
            placeHolder = Res.string.meeting_write_category_placeholder.value,
            onItemSelect = { onCategorySelect(it.id) },
            selectedItem = selectedCategory,
            itemKey = { it.id },
            itemName = { it.name },
        )

        WriteDialogSelection(
            items = locations,
            label = Res.string.meeting_write_location_label.value,
            placeHolder = Res.string.meeting_write_location_placeholder.value,
            onItemSelect = { onLocationSelect(it.code) },
            selectedItem = selectedLocation,
            itemKey = { it.code },
            itemName = { it.name },
        )
    }
}

@Composable
private fun MeetingFormSelection(
    form: MeetingFormUi,
    onAction: (MeetingPostWriteAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MeetingDateForm(
            form = form,
            onAction = onAction,
        )
    }
}

@Composable
@Preview
private fun MeetingWriteScreenPreview() {
    AppTheme {
        MeetingPostContentScreen(
            state = MeetingPostContentState(),
            onAction = { },
            snackbarHost = { },
        )
    }
}
