package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeRadioButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.modifiers.bottomBorder
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.components.WriteInputSection
import com.parksupark.soomjae.features.post.presentation.models.CategoryUi
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.community_write_button_confirm_description
import com.parksupark.soomjae.features.post.presentation.resources.community_write_category_select_placeholder
import com.parksupark.soomjae.features.post.presentation.resources.community_write_navigate_up_description
import com.parksupark.soomjae.features.post.presentation.resources.write_category_select_label
import kotlinx.collections.immutable.ImmutableList

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
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            WriteInputSection(
                title = state.inputTitle,
                content = state.inputContent,
            )

            AdditionalInfoSelection(
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelect = { categoryId ->
                    onAction(CommunityWriteAction.OnCategorySelected(categoryId))
                },
            )
        }
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
    onCategorySelect: (Long) -> Unit,
) {
    var isCategoryVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        AdditionalInfoItem(
            label = Res.string.write_category_select_label.value,
        ) {
            OutlinedButton(
                onClick = { isCategoryVisible = categories.isNotEmpty() && true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = Color.Transparent,
                    contentColor = SoomjaeTheme.colorScheme.text2,
                ),
                border = BorderStroke(1.dp, SoomjaeTheme.colorScheme.divider1),
            ) {
                val modifier = Modifier.fillMaxWidth()
                if (selectedCategory == null) {
                    Text(
                        text = Res.string.community_write_category_select_placeholder.value,
                        style = LocalTextStyle.current.copy(
                            color = SoomjaeTheme.colorScheme.text4,
                        ),
                        modifier = modifier,
                    )
                } else {
                    Text(text = selectedCategory.name, modifier = modifier)
                }
            }
        }
    }

    if (isCategoryVisible) {
        CategoryDialog(
            setIsCategoryVisible = { isCategoryVisible = it },
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelect = onCategorySelect,
        )
    }
}

@Composable
private fun AdditionalInfoItem(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    SoomjaeHorizontalDivider()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp),
    ) {
        Text(label, style = SoomjaeTheme.typography.labelL)

        content()
    }
}

@Composable
private fun CategoryDialog(
    setIsCategoryVisible: (Boolean) -> Unit,
    categories: ImmutableList<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelect: (Long) -> Unit,
) {
    Dialog(onDismissRequest = { setIsCategoryVisible(false) }) {
        SoomjaeSurface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(items = categories, key = { it.id }) { category ->
                    CategoryItem(
                        category = category,
                        selected = selectedCategory?.id == category.id,
                        onCategorySelect = {
                            onCategorySelect(category.id)
                            setIsCategoryVisible(false)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryUi,
    selected: Boolean,
    onCategorySelect: () -> Unit,
) {
    SoomjaeListItem(
        headlineContent = { Text(category.name) },
        modifier = Modifier.clickable {
            onCategorySelect()
        },
        leadingContent = {
            SoomjaeRadioButton(
                selected = selected,
                onClick = null,
            )
        },
    )
}
