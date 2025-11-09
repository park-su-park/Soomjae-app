package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.modifiers.bottomBorder
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.PostWriteLayout
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteDialogSelection
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputContent
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteInputTitle
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionButton
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_button_confirm_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_category_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_category_placeholder
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_location_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_location_placeholder
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_meeting_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_meeting_placeholder
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_write_navigate_up_description
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
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
                    meeting = state.meeting,
                    onCreateMeetingClick = {
                        onAction(MeetingPostWriteAction.OnCreateMeetingPostClick)
                    },
                    categories = state.categories,
                    selectedCategory = state.selectedCategory,
                    onCategorySelect = { onAction(MeetingPostWriteAction.OnCategorySelect(it)) },
                    locations = state.locations,
                    selectedLocation = state.selectedLocation,
                    onLocationSelect = { onAction(MeetingPostWriteAction.OnLocationSelect(it)) },
                    modifier = Modifier.bottomBorder(SoomjaeTheme.colorScheme.divider1, 1.dp),
                )
            },
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
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
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
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = Res.string.meeting_write_button_confirm_description.value,
                )
            }
        },
    )
}

@Composable
private fun AdditionalInfoSelection(
    meeting: MeetingCreationUi?,
    onCreateMeetingClick: () -> Unit,
    categories: ImmutableList<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelect: (categoryId: Long) -> Unit,
    locations: ImmutableList<LocationUi>,
    selectedLocation: LocationUi?,
    onLocationSelect: (locationCode: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MeetingSelection(
            meeting = meeting,
            onClick = onCreateMeetingClick,
        )

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
private fun MeetingSelection(
    meeting: MeetingCreationUi?,
    onClick: () -> Unit,
) {
    WriteSelectionButton(
        label = Res.string.meeting_write_meeting_label.value,
        onClick = onClick,
        buttonText = {
            if (meeting == null) {
                Text(
                    text = Res.string.meeting_write_meeting_placeholder.value,
                    modifier = Modifier.fillMaxWidth(),
                    color = SoomjaeTheme.colorScheme.text3,
                )
            } else {
                Text(
                    text = meeting.toDisplayString(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    )
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
